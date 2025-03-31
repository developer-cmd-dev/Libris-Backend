package com.developerDev.Libris.Scheduler;

import com.developerDev.Libris.ExceptionHandler.CustomException;
import com.developerDev.Libris.JsonResposeEntity.RentedBooksData;
import com.developerDev.Libris.Repository.RentedBooksRepository;
import com.developerDev.Libris.Service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Slf4j
@Component
public class RentedBooksDueDateScheduler {

    private final RentedBooksRepository repository;
    private final EmailService emailService;

    public RentedBooksDueDateScheduler(RentedBooksRepository repository, EmailService emailService) {
        this.repository = repository;
        this.emailService = emailService;
    }


    @Scheduled(fixedRate = 1000*60)
    public void returnRentedBook(){
      try {
          List<RentedBooksData> books = repository.findAll();
          LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
          for (RentedBooksData booksValue : books) {
              LocalDateTime dueDate = booksValue.getDueDate().truncatedTo(ChronoUnit.MINUTES);
              if (now.isEqual(dueDate)) {
                  emailService.returnRentBookMail(booksValue.getUsername(),booksValue.getRentalCost());
              }
          }
      }catch (Exception e){
          throw new CustomException("Something went wrong to return book", HttpStatus.INTERNAL_SERVER_ERROR);
      }


    }



}
