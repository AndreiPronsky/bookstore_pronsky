package online.javaclass.bookstore.data.entities;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Book {
    private Long id;
    private String title;
    private String author;
    private String isbn;
    private Genre genre;
    private Cover cover;
    private Integer pages;
    private BigDecimal price;
    private BigDecimal rating;

}
