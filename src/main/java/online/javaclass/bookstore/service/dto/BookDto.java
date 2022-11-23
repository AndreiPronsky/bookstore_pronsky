package online.javaclass.bookstore.service.dto;

import lombok.Data;
import online.javaclass.bookstore.data.entities.Cover;
import online.javaclass.bookstore.data.entities.Genre;

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
}
