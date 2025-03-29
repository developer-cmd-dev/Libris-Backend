package com.developerDev.Libris.Service;

import com.developerDev.Libris.Entity.User;
import com.developerDev.Libris.ExceptionHandler.CustomException;
import com.developerDev.Libris.JsonResposeEntity.BooksDataResponse;
import com.developerDev.Libris.JsonResposeEntity.RentedBooksData;
import com.developerDev.Libris.Repository.BooksRepository;
import com.developerDev.Libris.Repository.RentedBooksRepository;
import com.developerDev.Libris.Repository.UserReopository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Function;
import java.util.function.Supplier;

@Service
public class RentBookService {

    private final RentedBooksRepository rentedBooksRepository;
    private final BooksRepository booksRepository;
    private final UserReopository userReopository;
    public RentBookService(RentedBooksRepository rentedBooksRepository, BooksRepository booksRepository, UserReopository userReopository) {
        this.rentedBooksRepository = rentedBooksRepository;
        this.booksRepository = booksRepository;
        this.userReopository = userReopository;
    }
    Supplier<String> getAuthenticatedName=()-> SecurityContextHolder.getContext().getAuthentication().getName();

    Function<Double,Boolean> doPayment=(price->true);

    @Transactional
    public void rentBook(RentedBooksData data, String bookId){
        BooksDataResponse.Book getBook = booksRepository.findById(Integer.parseInt(bookId)).orElse(null);
        User user=userReopository.findByEmail(getAuthenticatedName.get()).orElse(null);
        if(getBook!=null){
            data.setBookId(getBook.getId());
            data.setUsername(getAuthenticatedName.get());
            if(Boolean.TRUE.equals(doPayment.apply(getBook.getPrice()))){
                data.setPaymentStatus("Completed");
            }else{
                data.setPaymentStatus("Canceled");
            }

          RentedBooksData response =  rentedBooksRepository.save(data);


        }

        throw new CustomException("Something went wrong to find book data", HttpStatus.NOT_FOUND);

    }



}
