package online.javaclass.bookstore.data.entities;

import online.javaclass.bookstore.data.converters.bookConverters.CoverConverter;
import online.javaclass.bookstore.data.converters.bookConverters.GenreConverter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "author")
    private String author;

    @Column(name = "isbn")
    private String isbn;

    @Column(name = "genre_id")
    @Convert(converter = GenreConverter.class)
    private Genre genre;

    @Column(name = "cover_id")
    @Convert(converter = CoverConverter.class)
    private Cover cover;

    @Column(name = "pages")
    private Integer pages;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "rating")
    private BigDecimal rating;

    @Table(name = "covers")
    public enum Cover {
        SOFT,
        HARD,
        SPECIAL
    }

    @Table(name = "genres")
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

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id) && Objects.equals(title, book.title) && Objects.equals(author, book.author) && Objects.equals(isbn, book.isbn) && genre == book.genre && cover == book.cover && Objects.equals(pages, book.pages) && Objects.equals(price, book.price) && Objects.equals(rating, book.rating);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, author, isbn, genre, cover, pages, price, rating);
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", isbn='" + isbn + '\'' +
                ", genre=" + genre +
                ", cover=" + cover +
                ", pages=" + pages +
                ", price=" + price +
                ", rating=" + rating +
                '}';
    }
}
