package online.javaclass.bookstore.data.converters.bookConverters;

import online.javaclass.bookstore.data.entities.Book;

import javax.persistence.AttributeConverter;

public class CoverConverter implements AttributeConverter<Book.Cover, Integer> {
    @Override
    public Integer convertToDatabaseColumn(Book.Cover cover) {
        Integer coverId = null;
        switch (cover) {
            case SOFT -> coverId = 1;
            case HARD -> coverId = 2;
            case SPECIAL -> coverId = 3;
        }
        return coverId;
    }

    @Override
    public Book.Cover convertToEntityAttribute(Integer coverId) {
        Book.Cover cover = null;
        switch (coverId) {
            case 1 -> cover = Book.Cover.SOFT;
            case 2 -> cover = Book.Cover.HARD;
            case 3 -> cover = Book.Cover.SPECIAL;
        }
        return cover;
    }
}
