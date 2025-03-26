package com.developerDev.Libris.JsonResposeEntity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class BooksDataResponse {
    private int count;
    private String next;
    private String previous;
    private List<Book> results;

    @Data
    public static class Book {
        private int id;
        private String title;
        private List<Author> authors;
        private List<String> summaries;
        private List<?> translators;
        private List<String> subjects;
        private List<String> bookshelves;
        private List<String> languages;
        private boolean copyright;
        @JsonProperty("media_type")
        private String mediaType;
        private Map<String, String> formats;
        @JsonProperty("download_count")
        private int downloadCount;
    }

    @Data
    public static class Author {
        private String name;
        @JsonProperty("birth_year")
        private Integer birthYear;
        @JsonProperty("death_year")
        private Integer deathYear;
    }
}
