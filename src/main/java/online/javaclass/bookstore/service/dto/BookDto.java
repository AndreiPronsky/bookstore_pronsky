package online.javaclass.bookstore.service.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
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
    private String filePath;
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
                ", fileName=" + filePath +
                '}';
    }
}
