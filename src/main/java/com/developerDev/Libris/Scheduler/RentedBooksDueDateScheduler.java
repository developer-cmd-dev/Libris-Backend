package com.developerDev.Libris.Scheduler;

import com.developerDev.Libris.ExceptionHandler.CustomException;
import com.developerDev.Libris.JsonResposeEntity.RentedBooksData;
import com.developerDev.Libris.Repository.RentedBooksRepository;
import com.developerDev.Libris.Service.EmailService;
import com.developerDev.Libris.Service.RentBookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@Component
public class RentedBooksDueDateScheduler {

    private final RentedBooksRepository repository;
    private final EmailService emailService;
    private final RentBookService rentBookService;
    public final List<RentedBooksData> cacheData=new ArrayList<>();

    public RentedBooksDueDateScheduler(RentedBooksRepository repository, EmailService emailService,
                                       RentBookService rentBookService) {
        this.repository = repository;
        this.emailService = emailService;
        this.rentBookService = rentBookService;
    }


//    @Scheduled(fixedRate = 1000 * 60)
//    public void returnRentedBook() {
//        try {
//
//            if(cacheData.isEmpty()){
//                List<RentedBooksData> rentedBooks = repository.findAll();
//                cacheData.addAll(rentedBooks);
//            }
//
//
//            LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
//            for (RentedBooksData booksValue : cacheData) {
//            if(!booksValue.isReturned()){
//                LocalDateTime dueDate = booksValue.getDueDate().truncatedTo(ChronoUnit.MINUTES);
//                if (now.isEqual(dueDate)) {
//                    RentedBooksData data = rentBookService.returnBook(booksValue.getId());
//                    if (data != null) {
//                        data.setReturned(true);
//                        data.setPaymentStatus("not completed");
//                        repository.save(data);
//                        emailService.returnRentBookMail(booksValue.getUsername(), booksValue.getRentalCost());
//                    }
//                }
//            }
//            }
//        } catch (Exception e) {
//            throw new CustomException("Something went wrong to return book", HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//
//    }


}
