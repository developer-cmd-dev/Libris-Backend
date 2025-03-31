package com.developerDev.Libris.Service;

import com.developerDev.Libris.ExceptionHandler.CustomException;
import com.developerDev.Libris.JsonResposeEntity.BooksDataResponse;
import com.developerDev.Libris.JsonResposeEntity.RentedBooksData;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.function.Supplier;

@Service
public class EmailService {


    private final JavaMailSender javaMailSender;

    private final   TemplateEngine templateEngine;


    @Autowired
    public EmailService(JavaMailSender javaMailSender, TemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }




    public void sendRentConfirmationMail(String to, BooksDataResponse.Book data,RentedBooksData rentedData)  {

        MimeMessage message = javaMailSender.createMimeMessage();
       try{
           MimeMessageHelper helper = new MimeMessageHelper(message, true);

           helper.setTo(to);
           helper.setSubject("\"Libris Rental Confirmation: "+data.getTitle()+ " is Ready for You!\"\n" +
                   "\n");
           helper.setFrom("librisopenlibrary@gmail.com");
           Context context = new Context();
           context.setVariable("userName", rentedData.getUsername());
           context.setVariable("bookTitle", data.getTitle());
           context.setVariable("rentalDate", rentedData.getRentalDate());
           context.setVariable("dueDate", rentedData.getDueDate());

           // Process the HTML template
           String htmlContent = templateEngine.process("rental-confirmation.html", context);
           helper.setText(htmlContent, true); // Enable HTML

           // Send the email
           javaMailSender.send(message);

       }catch (Exception e){
           throw new CustomException(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
       }

    }


    public void returnRentBookMail(String to,double amount){
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message,true);
            helper.setTo(to);
            helper.setSubject("Libris Open Library");
            helper.setFrom("librisopenlibrary@gmail.com");
            Context context = new Context();
            context.setVariable("refundAmount",(int) amount);
            String html = templateEngine.process("return-rented-book.html",context);
            helper.setText(html,true);
            javaMailSender.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }

}
