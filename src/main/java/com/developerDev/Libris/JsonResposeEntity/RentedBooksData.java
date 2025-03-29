package com.developerDev.Libris.JsonResposeEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
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
public class RentedBooksData {
    @Id
    private ObjectId id;
    @DBRef
    private int bookId;
    private LocalDateTime rentalDate;
    private LocalDateTime dueDate;
    private boolean isReturned = false;
    private double rentalCost;
    private String paymentStatus;
    private String username;
}
