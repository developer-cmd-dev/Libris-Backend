package com.developerDev.Libris.Repository;

import com.developerDev.Libris.Entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserReopository extends MongoRepository<User, ObjectId> {
    Optional<User>findByEmail(String email);
}
