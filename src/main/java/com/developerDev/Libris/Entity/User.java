package com.developerDev.Libris.Entity;


import com.developerDev.Libris.JsonResposeEntity.BooksDataResponse;
import com.developerDev.Libris.JsonResposeEntity.RentedBooksData;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class User {
    @Id
    private ObjectId id;
    @NonNull
    private String name;
    @NonNull
    private String email;
    private String password;
    private List<String> roles;
    @DBRef
    private List<BooksDataResponse.Book>booksData=new ArrayList<>();
    @DBRef
    private List<RentedBooksData> rentedBooks =new ArrayList<>();
    @DBRef List<?> purchasedBooks=new ArrayList<>();

}
