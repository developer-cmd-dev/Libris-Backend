package com.developerDev.Libris.Repository;

import com.developerDev.Libris.JsonResposeEntity.RentedBooksData;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RentedBooksRepository extends MongoRepository<RentedBooksData, ObjectId> {

    RentedBooksData findById(String id);

}
