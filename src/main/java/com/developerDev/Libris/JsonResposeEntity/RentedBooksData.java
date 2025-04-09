package com.developerDev.Libris.JsonResposeEntity;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "rented_books")
@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RentedBooksData {
    @Id
    private ObjectId id;
    private int bookId;
    private LocalDateTime rentalDate=LocalDateTime.now();
    private LocalDateTime dueDate;
    private boolean isReturned = false;
    private double rentalCost;
    private String paymentStatus;
    private String username;
}
