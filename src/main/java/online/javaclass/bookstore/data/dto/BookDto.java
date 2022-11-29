package online.javaclass.bookstore.data.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BookDto {
    private Long id;
    private String title;
    private String author;
    private String isbn;
    private Genre genre;
    private Cover cover;
    private Integer pages;
    private BigDecimal price;
    private BigDecimal rating;

    public enum Cover {
        SOFT,
        HARD,
        SPECIAL
    }

    public enum Genre {
        FICTION,
        MYSTERY,
        THRILLER,
        HORROR,
        HISTORICAL,
        ROMANCE,
        WESTERN,
        FLORISTICS,
        SCIENCE_FICTION,
        DYSTOPIAN,
        REALISM,
        RELIGION,
        MEDICINE,
        ENGINEERING,
        ART
    }
}
