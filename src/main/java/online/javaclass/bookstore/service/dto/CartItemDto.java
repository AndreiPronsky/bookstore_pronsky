package online.javaclass.bookstore.service.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItemDto {
    private Long id;
    private String title;
    private String author;
    private String isbn;
    private BookDto.Genre genre;
    private BookDto.Cover cover;
    private Integer pages;
    private BigDecimal price;
    private BigDecimal rating;
    private Integer quantity;
}
