package com.developerDev.Libris.Service;

import com.developerDev.Libris.Entity.User;
import com.developerDev.Libris.ExceptionHandler.CustomException;
import com.developerDev.Libris.JsonResposeEntity.BooksDataResponse;
import com.developerDev.Libris.JsonResposeEntity.RentedBooksData;
import com.developerDev.Libris.Repository.BooksRepository;
import com.developerDev.Libris.Repository.BooksRepositoryImpl;
import com.developerDev.Libris.Repository.RentedBooksRepository;
import com.developerDev.Libris.Repository.UserReopository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
    private final BooksRepositoryImpl query;

    @Autowired
    public RentBookService(RentedBooksRepository rentedBooksRepository, BooksRepository booksRepository, UserReopository userReopository, EmailService emailService, BooksRepositoryImpl query) {
        this.rentedBooksRepository = rentedBooksRepository;
        this.booksRepository = booksRepository;
        this.userReopository = userReopository;
        this.emailService = emailService;
        this.query = query;

    }
    Supplier<String> getAuthenticatedName=()-> SecurityContextHolder.getContext().getAuthentication().getName();

    Function<Double,Boolean> doPayment=(price->true);

    @Transactional
    public User rentBook(RentedBooksData data, String bookId){
        List<RentedBooksData> rentedBooks =query.findByBookId(Integer.parseInt(bookId));
        User user=userReopository.findByEmail(getAuthenticatedName.get()).orElse(null);
        BooksDataResponse.Book getBook = booksRepository.findById(Integer.parseInt(bookId)).orElse(null);
       if(rentedBooks.isEmpty()){
        if(getBook!=null){
            data.setBookId(getBook.getId());
            data.setUsername(getAuthenticatedName.get());
            if(Boolean.TRUE.equals(doPayment.apply(getBook.getPrice()))){
                data.setPaymentStatus("Completed");
            }else{
                data.setPaymentStatus("Canceled");
                throw new CustomException("Payment cancelled!",HttpStatus.NOT_ACCEPTABLE);
            }

          RentedBooksData response =  rentedBooksRepository.save(data);

            user.getRentedBooks().add(response);

          return userReopository.save(user);
        }
       }else{

            RentedBooksData rentedBooksData = rentedBooks.get(0);
            assert user != null;
            rentedBooksData.setUsername(user.getEmail());
            rentedBooksData.setPaymentStatus("completed");
            rentedBooksData.setReturned(false);
            rentedBooksData.setRentalDate(data.getRentalDate());
            rentedBooksData.setDueDate(data.getDueDate());
            RentedBooksData save = rentedBooksRepository.save(rentedBooksData);
            user.getRentedBooks().add(save);
           try{
               emailService.sendRentConfirmationMail(getAuthenticatedName.get(),getBook,data);
           }catch (Exception e){
               throw new CustomException(e.getLocalizedMessage(),HttpStatus.BAD_REQUEST);
           }
           return userReopository.save(user);
       }

//
        throw new CustomException("Something went wrong to find book data", HttpStatus.NOT_FOUND);

    }

    @Transactional
    public RentedBooksData returnBook(ObjectId id){
        RentedBooksData data = rentedBooksRepository.findById(id).orElse(null);
        try{
            if (data!=null){
                data.setReturned(true);
                data.setPaymentStatus("not completed");
               return rentedBooksRepository.save(data);
            }
        } catch (Exception e) {
            throw new CustomException("Something went wrong to return book!",HttpStatus.BAD_REQUEST);
        }
        return data;
    }


    @Transactional
    public RentedBooksData returnBook(String bookId){
        int id = Integer.parseInt(bookId);
      try{
          String username = getAuthenticatedName.get();
          User user= userReopository.findByEmail(username).orElse(null);
          List<RentedBooksData> list = user.getRentedBooks();
            for (RentedBooksData data:list){
            if(data.getBookId()==id&&data.getUsername().equals(username)){
                data.setReturned(true);
                data.setPaymentStatus("not completed");
               return rentedBooksRepository.save(data);
            }
            }

          throw new CustomException("No book found with this id!",HttpStatus.NOT_FOUND);

      }catch (Exception e){
          log.error(e.getMessage());
          throw new CustomException("Something went wrong!",HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }


    public List<RentedBooksData>getRentedBooksData(){
        String userName = getAuthenticatedName.get();
        Optional<User> findUser = userReopository.findByEmail(userName);
        if (findUser.isPresent()){
            return findUser.get().getRentedBooks();
        }
        throw new CustomException("User not found",HttpStatus.NOT_FOUND);
    }




}
