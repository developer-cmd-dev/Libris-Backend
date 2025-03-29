package com.developerDev.Libris.Service;

import com.developerDev.Libris.ExceptionHandler.CustomException;
import com.developerDev.Libris.JsonResposeEntity.BooksDataResponse;
import com.developerDev.Libris.JsonResposeEntity.RentedBooksData;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailService {


    private final JavaMailSender javaMailSender;

    private final   TemplateEngine templateEngine;

//    @Value("${mail.from}")
//    private String fromMail;

    @Autowired
    public EmailService(JavaMailSender javaMailSender, TemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }


    public void sendMail(String to, BooksDataResponse.Book data,RentedBooksData rentedData)  {

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
           context.setVariable("authorName", data.getAuthors().get(0));
           context.setVariable("rentalDate", rentedData.getRentalDate());
           context.setVariable("dueDate", rentedData.getDueDate());
           context.setVariable("rentalId", rentedData.getId());

           // Process the HTML template
           String htmlContent = templateEngine.process("rental-confirmation.html", context);
           helper.setText(htmlContent, true); // Enable HTML

           // Send the email
           javaMailSender.send(message);
       }catch (Exception e){
           throw new CustomException(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
       }

    }

}
