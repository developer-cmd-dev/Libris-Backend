package com.developerDev.Libris.Repository;

import com.developerDev.Libris.JsonResposeEntity.BooksDataResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BooksRepositoryImpl {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<BooksDataResponse.Book> findByTitle(String value){
        Query query = new Query();
         query.addCriteria(Criteria.where("title").regex(".*"+value+".*"));
         return mongoTemplate.find(query, BooksDataResponse.Book.class);
    }


}
