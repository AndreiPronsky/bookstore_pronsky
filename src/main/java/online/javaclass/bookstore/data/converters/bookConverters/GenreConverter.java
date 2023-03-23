package online.javaclass.bookstore.data.converters.bookConverters;

import online.javaclass.bookstore.data.entities.Book;

import javax.persistence.AttributeConverter;

import static online.javaclass.bookstore.data.entities.Book.Genre.*;

public class GenreConverter implements AttributeConverter<Book.Genre, Integer> {
    @Override
    public Integer convertToDatabaseColumn(Book.Genre genre) {
        Integer genreId = null;
        switch (genre) {
            case FICTION -> genreId = 1;
            case MYSTERY -> genreId = 2;
            case THRILLER -> genreId = 3;
            case HORROR -> genreId = 4;
            case HISTORICAL -> genreId = 5;
            case ROMANCE -> genreId = 6;
            case WESTERN -> genreId = 7;
            case FLORISTICS -> genreId = 8;
            case SCIENCE_FICTION -> genreId = 9;
            case DYSTOPIAN -> genreId = 10;
            case REALISM -> genreId = 11;
            case RELIGION -> genreId = 12;
            case MEDICINE -> genreId = 13;
            case ENGINEERING -> genreId = 14;
            case ART -> genreId = 15;
        }
        return genreId;
    }

    @Override
    public Book.Genre convertToEntityAttribute(Integer genreId) {
        Book.Genre genre = null;
        switch (genreId) {
            case 1 -> genre = Book.Genre.FICTION;
            case 2 -> genre = MYSTERY;
            case 3 -> genre = THRILLER;
            case 4 -> genre = HORROR;
            case 5 -> genre = HISTORICAL;
            case 6 -> genre = ROMANCE;
            case 7 -> genre = WESTERN;
            case 8 -> genre = FLORISTICS;
            case 9 -> genre = SCIENCE_FICTION;
            case 10 -> genre = DYSTOPIAN;
            case 11 -> genre = REALISM;
            case 12 -> genre = RELIGION;
            case 13 -> genre = MEDICINE;
            case 14 -> genre = ENGINEERING;
            case 15 -> genre = ART;
        }
        return genre;
    }
}
