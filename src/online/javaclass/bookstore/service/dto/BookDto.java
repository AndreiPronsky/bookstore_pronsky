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
    private int pages;
    private BigDecimal price;
    private BigDecimal rating;

    public BookDto() {

    }

    public BookDto(Long id, String title, String author, String isbn, Cover cover, int pages, BigDecimal price, BigDecimal rating) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.cover = cover;
        this.pages = pages;
        this.price = price;
        this.rating = rating;
    }

    public BookDto(String title, String author, String isbn, Cover cover, int pages, BigDecimal price, BigDecimal rating) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.cover = cover;
        this.pages = pages;
        this.price = price;
        this.rating = rating;
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

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
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
        return Objects.equals(id, bookDto.id) && Objects.equals(title, bookDto.title) && Objects.equals(author, bookDto.author)
                && Objects.equals(isbn, bookDto.isbn) && Objects.equals(price, bookDto.price);
    }

    @Override
    public String toString() {
        return "online.javaclass.bookstore.service.dto.BookDto { id = " + id + " | " +
                "title = " + title + " | " +
                "author = " + author + " | " +
                "isbn = " + isbn + " | " +
                "cover = " + cover + " | " +
                "pages = " + pages + " | " +
                "price = " + price + " | " +
                "rating = " + rating + " }";
    }
}
