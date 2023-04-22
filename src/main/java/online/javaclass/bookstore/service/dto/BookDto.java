package online.javaclass.bookstore.service.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.util.Objects;

public class BookDto {
    private Long id;
    @NotBlank(message = "{error.invalid_title}")
    private String title;
    @NotBlank(message = "{error.invalid_author}")
    private String author;
    @Pattern(regexp = "^(?=(?:\\D*\\d){10}(?:(?:\\D*\\d){3})?$)[\\d-]+$", message = "{error.invalid_isbn}")
    private String isbn;
    private Genre genre;
    private Cover cover;
    private Integer pages;
    @NotNull(message = "{error.invalid_price}")
    private BigDecimal price;
    private BigDecimal rating;

    private boolean available;

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

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookDto bookDto = (BookDto) o;
        return Objects.equals(id, bookDto.id) && Objects.equals(title, bookDto.title) && Objects.equals(author, bookDto.author) && Objects.equals(isbn, bookDto.isbn) && genre == bookDto.genre && cover == bookDto.cover && Objects.equals(pages, bookDto.pages) && Objects.equals(price, bookDto.price) && Objects.equals(rating, bookDto.rating);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, author, isbn, genre, cover, pages, price, rating);
    }

    @Override
    public String toString() {
        return "BookDto{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", isbn='" + isbn + '\'' +
                ", genre=" + genre +
                ", cover=" + cover +
                ", pages=" + pages +
                ", price=" + price +
                ", rating=" + rating +
                ", available=" + available +
                '}';
    }
}
