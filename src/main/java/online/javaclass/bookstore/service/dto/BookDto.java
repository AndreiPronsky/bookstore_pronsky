package online.javaclass.bookstore.service.dto;

import online.javaclass.bookstore.data.entities.Cover;

import java.math.BigDecimal;
import java.util.Objects;

public class BookDto {
    private Long id;
    private String title;
    private String author;
    private String isbn;
    private Cover cover;
    private Integer pages;
    private BigDecimal price;
    private BigDecimal rating;

    public BookDto() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Cover getCover() {
        return cover;
    }

    public void setCover(Cover cover) {
        this.cover = cover;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getRating() {
        return rating;
    }

    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }


    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, author, isbn, price);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof BookDto bookDto)) return false;
        return Objects.equals(title, bookDto.title) && Objects.equals(author, bookDto.author)
                && Objects.equals(isbn, bookDto.isbn) && Objects.equals(price, bookDto.price);
    }
}
