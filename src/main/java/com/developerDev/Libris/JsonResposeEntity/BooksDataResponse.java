package com.developerDev.Libris.JsonResposeEntity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class BooksDataResponse {
    private int count;
    private String next;
    private String previous;
    private List<Book> results;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class Book {
        @Id
        @JsonProperty("id")
        private int id;
        private String title;
        private List<Author> authors;
        private List<String> summaries;
        private List<?> translators;
        private List<String> subjects;
        private List<String> bookshelves;
        private List<String> languages;
        private boolean copyright;
        private double price;
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
