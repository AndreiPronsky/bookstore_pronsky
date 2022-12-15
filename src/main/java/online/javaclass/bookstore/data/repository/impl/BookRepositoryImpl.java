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
    public Book findById(Long id) {
        BookDto bookDto = bookDao.findById(id);
        return mapper.toEntity(bookDto);
    }

    @Override
    public Book findByIsbn(String isbn) {
        BookDto bookDto = bookDao.findByIsbn(isbn);
        return mapper.toEntity(bookDto);
    }

    @Override
    public List<Book> findByAuthor(String author) {
        List<BookDto> bookDtos = bookDao.findByAuthor(author);
        List<Book> books = new ArrayList<>();
        for (BookDto bookDto : bookDtos) {
            books.add(mapper.toEntity(bookDto));
        }
        return books;
    }

    @Override
    public List<Book> findByAuthor(String author, int limit, int offset) {
        List<BookDto> bookDtos = bookDao.findByAuthor(author, limit, offset);
        List<Book> books = new ArrayList<>();
        for (BookDto bookDto : bookDtos) {
            books.add(mapper.toEntity(bookDto));
        }
        return books;
    }

    @Override
    public List<Book> findAll() {
        List<BookDto> bookDtos = bookDao.findAll();
        List<Book> books = new ArrayList<>();
        for (BookDto bookDto : bookDtos) {
            books.add(mapper.toEntity(bookDto));
        }
        return books;
    }

    @Override
    public List<Book> findAll(int limit, int offset) {
        List<BookDto> bookDtos = bookDao.findAll(limit, offset);
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
}
