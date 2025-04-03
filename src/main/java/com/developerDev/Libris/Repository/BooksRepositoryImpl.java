package com.developerDev.Libris.Repository;

import com.developerDev.Libris.JsonResposeEntity.BooksDataResponse;
import com.developerDev.Libris.JsonResposeEntity.RentedBooksData;
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

    public List<RentedBooksData> findByBookId(int value){
        Query query = new Query();
        Criteria criteria = new Criteria();
        query.addCriteria(criteria.andOperator(Criteria.where("bookId").is(value),
                Criteria.where("isReturned").is(true)));
        return mongoTemplate.find(query,RentedBooksData.class);
    }
//
//    public List<RentedBooksData> findByUsername(int value,String username){
//        Query query = new Query();
//        Criteria criteria = new Criteria();
//        query.addCriteria(criteria.andOperator(Criteria.where("bookId").is(value),Criteria.where("username")).is(username));
//        return mongoTemplate.find(query,RentedBooksData.class);
//    }


}
