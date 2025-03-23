package com.developerDev.Libris.Entity;


import lombok.*;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user")
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
    private String roles = "USER";
}
