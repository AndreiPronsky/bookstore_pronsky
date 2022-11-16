package online.javaclass.bookstore.data.entities;

import java.math.BigDecimal;
import java.util.Objects;

public class Book {
    private Long id;
    private String title;
    private String author;
    private String isbn;
    private Cover cover;
    private Integer pages;
    private BigDecimal price;
    private BigDecimal rating;

    public Book() {

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
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Book book)) {
            return false;
        }
        return Objects.equals(id, book.id) && Objects.equals(title, book.title) && Objects.equals(author, book.author)
                && Objects.equals(isbn, book.isbn) && Objects.equals(price, book.price);
    }
}
