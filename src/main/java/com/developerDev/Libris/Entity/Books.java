package com.developerDev.Libris.Entity;

import com.developerDev.Libris.JsonResposeEntity.BooksDataResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

@Document(collection = "books_collection")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Books {
    @Id
    @JsonProperty("id")
    private int id;
    private String title;
    private List<BooksDataResponse.Author> authors;
    private List<String> summaries;
    private List<?> translators;
    private List<String> subjects;
    private List<String> bookshelves;
    private List<String> languages;
    private double price;
    private boolean copyright;
    @JsonProperty("media_type")
    private String mediaType;
    private Map<String, String> formats;
    @JsonProperty("download_count")
    private int downloadCount;



    @Data
    public static class Author {
        private String name;
        @JsonProperty("birth_year")
        private Integer birthYear;
        @JsonProperty("death_year")
        private Integer deathYear;
    }

}
