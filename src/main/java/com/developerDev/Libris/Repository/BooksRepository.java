package com.developerDev.Libris.Repository;
import com.developerDev.Libris.JsonResposeEntity.BooksDataResponse;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface BooksRepository extends MongoRepository<BooksDataResponse.Book, Integer> {
    Optional<BooksDataResponse.Book> findById();
}
