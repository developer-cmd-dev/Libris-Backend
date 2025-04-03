package com.developerDev.Libris.Repository;

import com.developerDev.Libris.JsonResposeEntity.BooksDataResponse;
import com.developerDev.Libris.JsonResposeEntity.RentedBooksData;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(MockitoExtension.class)
class BooksRepositoryImplTest {

    @Mock
    private MongoTemplate mongoTemplate;

    @InjectMocks
    private BooksRepositoryImpl booksRepository;





    @Test
    void findByTitle() {
        BooksDataResponse.Book mock = BooksDataResponse.Book.builder().title("The secret with this").build();
        List<BooksDataResponse.Book> expectedBook = List.of(mock);

        when(booksRepository.findByTitle("the secret")).thenReturn(expectedBook);

        List<BooksDataResponse.Book> result = booksRepository.findByTitle("the secret");
        log.info(result.toString());
        assertNotNull(result);

    }

    @Test
    void findByBookId() {
        RentedBooksData mock = RentedBooksData.builder().bookId(2701).isReturned(false).build();
        List<RentedBooksData> list = List.of(mock);
        when(booksRepository.findByBookId(2701)).thenReturn(list);
        List<RentedBooksData> result = booksRepository.findByBookId(2701);
        log.info(result.toString());
        assertTrue(result.isEmpty());
    }
}