package com.developerDev.Libris.Repository;

import com.developerDev.Libris.JsonResposeEntity.BooksDataResponse;
import com.developerDev.Libris.Repository.BooksRepository;
import com.developerDev.Libris.Repository.BooksRepositoryImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class BooksRepoImplTest {

    @Mock
    private MongoTemplate mongoTemplate;

    @InjectMocks
    private BooksRepositoryImpl repository; // Replace with your actual class name

    @Test
    public void testFindByTitle() {
        // Setup test data
        BooksDataResponse.Book expectedBook = new BooksDataResponse.Book();
        expectedBook.setTitle("Simple Sabotage Field Manual");
        List<BooksDataResponse.Book> expectedList = List.of(expectedBook);

        // Mock the MongoTemplate behavior
        when(mongoTemplate.find(any(Query.class), eq(BooksDataResponse.Book.class)))
                .thenReturn(expectedList);

        // Execute the method
        List<BooksDataResponse.Book> result = repository.findByTitle();
        log.info(result.toString());

        // Verify the results
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Simple Sabotage Field Manual", result.get(0).getTitle());
    }
}