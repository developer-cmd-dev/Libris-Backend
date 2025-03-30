package com.developerDev.Libris.Scheduler;

import com.developerDev.Libris.JsonResposeEntity.RentedBooksData;
import com.developerDev.Libris.Repository.RentedBooksRepository;
import com.developerDev.Libris.Service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
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
        List<RentedBooksData> books = repository.findAll();
        LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        log.info(now.toString());
        for (RentedBooksData booksValue : books) {
            LocalDateTime dueDate = booksValue.getDueDate().truncatedTo(ChronoUnit.MINUTES);
            log.info(dueDate.toString());

            if (now.isEqual(dueDate)) {
                emailService.returnRentBookMail(booksValue.getUsername(),booksValue.getRentalCost());
            }
        }


    }



}
