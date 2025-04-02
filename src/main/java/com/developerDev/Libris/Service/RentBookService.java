package com.developerDev.Libris.Service;

import com.developerDev.Libris.Entity.User;
import com.developerDev.Libris.ExceptionHandler.CustomException;
import com.developerDev.Libris.JsonResposeEntity.BooksDataResponse;
import com.developerDev.Libris.JsonResposeEntity.RentedBooksData;
import com.developerDev.Libris.Repository.BooksRepository;
import com.developerDev.Libris.Repository.RentedBooksRepository;
import com.developerDev.Libris.Repository.UserReopository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

@Slf4j
@Service
public class RentBookService {

    private final RentedBooksRepository rentedBooksRepository;
    private final BooksRepository booksRepository;
    private final UserReopository userReopository;
    private final EmailService emailService;

    @Autowired
    public RentBookService(RentedBooksRepository rentedBooksRepository, BooksRepository booksRepository, UserReopository userReopository, EmailService emailService) {
        this.rentedBooksRepository = rentedBooksRepository;
        this.booksRepository = booksRepository;
        this.userReopository = userReopository;
        this.emailService = emailService;
    }
    Supplier<String> getAuthenticatedName=()-> SecurityContextHolder.getContext().getAuthentication().getName();

    Function<Double,Boolean> doPayment=(price->true);

    @Transactional
    public User rentBook(RentedBooksData data, String bookId){
        BooksDataResponse.Book getBook = booksRepository.findById(Integer.parseInt(bookId)).orElse(null);
        User user=userReopository.findByEmail(getAuthenticatedName.get()).orElse(null);
        if(getBook!=null&& user!=null){
            data.setBookId(getBook.getId());
            data.setUsername(getAuthenticatedName.get());
            if(Boolean.TRUE.equals(doPayment.apply(getBook.getPrice()))){
                data.setPaymentStatus("Completed");
            }else{
                data.setPaymentStatus("Canceled");
            }

          RentedBooksData response =  rentedBooksRepository.save(data);
            user.getRentedBooks().add(response);
            try{
                emailService.sendRentConfirmationMail(getAuthenticatedName.get(),getBook,data);
            }catch (Exception e){
                throw new CustomException(e.getLocalizedMessage(),HttpStatus.BAD_REQUEST);
            }
          return userReopository.save(user);
        }
        throw new CustomException("Something went wrong to find book data", HttpStatus.NOT_FOUND);

    }

    @Transactional
    public RentedBooksData returnBook(ObjectId id){
        RentedBooksData data = rentedBooksRepository.findById(id).orElse(null);
        try{
            if (data!=null){
                data.setReturned(true);
               return rentedBooksRepository.save(data);
            }
        } catch (Exception e) {
            throw new CustomException("Something went wrong to return book!",HttpStatus.BAD_REQUEST);
        }
        return data;
    }



}
