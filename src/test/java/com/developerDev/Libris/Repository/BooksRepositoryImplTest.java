package com.developerDev.Libris.Repository;

import com.developerDev.Libris.JsonResposeEntity.BooksDataResponse;
import lombok.extern.slf4j.Slf4j;
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
}