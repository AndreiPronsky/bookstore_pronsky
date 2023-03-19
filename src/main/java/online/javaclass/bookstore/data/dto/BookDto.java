package online.javaclass.bookstore.data.dto;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "books")
public class BookDto {
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

    @Enumerated(EnumType.STRING)
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @Enumerated(EnumType.STRING)
    @JoinColumn(name = "cover_id")
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
}
