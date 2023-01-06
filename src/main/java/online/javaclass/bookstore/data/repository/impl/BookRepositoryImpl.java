package online.javaclass.bookstore.data.repository.impl;

import lombok.RequiredArgsConstructor;
import online.javaclass.bookstore.data.EntityDtoMapperData;
import online.javaclass.bookstore.data.dao.BookDao;
import online.javaclass.bookstore.data.dto.BookDto;
import online.javaclass.bookstore.data.entities.Book;
import online.javaclass.bookstore.data.repository.BookRepository;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class BookRepositoryImpl implements BookRepository {
    private final BookDao bookDao;
    private final EntityDtoMapperData mapper;

    @Override
    public Book getById(Long id) {
        BookDto bookDto = bookDao.getById(id);
        return mapper.toEntity(bookDto);
    }

    @Override
    public Book getByIsbn(String isbn) {
        BookDto bookDto = bookDao.getByIsbn(isbn);
        return mapper.toEntity(bookDto);
    }

    @Override
    public List<Book> search(String input) {
        List<BookDto> bookDtos = bookDao.search(input);
        List<Book> books = new ArrayList<>();
        for (BookDto bookDto : bookDtos) {
            books.add(mapper.toEntity(bookDto));
        }
        return books;
    }

    @Override
    public List<Book> getByAuthor(String author, int limit, int offset) {
        List<BookDto> bookDtos = bookDao.getByAuthor(author, limit, offset);
        List<Book> books = new ArrayList<>();
        for (BookDto bookDto : bookDtos) {
            books.add(mapper.toEntity(bookDto));
        }
        return books;
    }

    @Override
    public List<Book> getAll(int limit, int offset) {
        List<BookDto> bookDtos = bookDao.getAll(limit, offset);
        List<Book> books = new ArrayList<>();
        for (BookDto bookDto : bookDtos) {
            books.add(mapper.toEntity(bookDto));
        }
        return books;
    }

    @Override
    public Book create(Book book) {
        BookDto bookDto = mapper.toDto(book);
        BookDto createdBook = bookDao.create(bookDto);
        return mapper.toEntity(createdBook);
    }

    @Override
    public Book update(Book book) {
        BookDto bookDto = mapper.toDto(book);
        BookDto updatedBook = bookDao.update(bookDto);
        return mapper.toEntity(updatedBook);
    }

    @Override
    public boolean deleteById(Long id) {
        return bookDao.deleteById(id);
    }

    @Override
    public Long count() {
        return bookDao.count();
    }
}
