package com.developerDev.Libris.Repository;

import com.developerDev.Libris.Entity.Books;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface BooksRepository extends MongoRepository<Books, Integer> {
    Optional<Books> findById();
}
