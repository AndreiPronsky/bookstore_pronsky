package online.javaclass.bookstore.service.impl;

import online.javaclass.bookstore.data.dao.BookDao;
import online.javaclass.bookstore.data.entities.Book;
import online.javaclass.bookstore.service.BookService;
import online.javaclass.bookstore.service.dto.BookDto;
import online.javaclass.bookstore.service.exceptions.AppException;

import java.util.List;

public class BookServiceImpl implements BookService {

    private final BookDao bookDao;

    public BookServiceImpl(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Override
    public BookDto create(BookDto bookDto) {
        Book book = toEntity(bookDto);
        Book created = bookDao.create(book);
        return toDto(created);

    }

    @Override
    public BookDto update(BookDto bookDto) {
        Book book = toEntity(bookDto);
        Book updated = bookDao.update(book);
        return toDto(updated);
    }

    @Override
    public BookDto getById(Long id) {
        Book book = bookDao.findById(id);
        if (book == null) throw new AppException("Unable to find book with id : " + id);
        return toDto(book);
    }

    @Override
    public BookDto getByIsbn(String isbn) {
        Book book = bookDao.findByIsbn(isbn);
        if (book == null) throw new AppException("Unable to find book with isbn : " + isbn);
        return toDto(book);
    }

    @Override
    public List<BookDto> getByAuthor(String author) {
        List<BookDto> bookDtos = bookDao.findByAuthor(author).stream()
                .map(this::toDto)
                .toList();
        if (bookDtos.isEmpty()) throw new AppException("Unable to find books by author : " + author);
        return bookDtos;
    }

    @Override
    public List<BookDto> getAll() {
        return bookDao.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        boolean deleted = bookDao.deleteById(id);
        if (!deleted) throw new AppException("Unable to delete book with id : " + id);
    }

    private Book toEntity(BookDto bookDto) {
        Book book = new Book();
        book.setId(bookDto.getId());
        book.setTitle(bookDto.getTitle());
        book.setAuthor(bookDto.getAuthor());
        book.setIsbn(bookDto.getIsbn());
        book.setCover(bookDto.getCover());
        book.setPages(bookDto.getPages());
        book.setPrice(bookDto.getPrice());
        book.setRating(bookDto.getRating());
        return book;
    }

    private BookDto toDto(Book book) {
        BookDto bookDto = new BookDto();
        bookDto.setId(book.getId());
        bookDto.setTitle(book.getTitle());
        bookDto.setAuthor(book.getAuthor());
        bookDto.setIsbn(book.getIsbn());
        bookDto.setCover(book.getCover());
        bookDto.setPages(book.getPages());
        bookDto.setPrice(book.getPrice());
        bookDto.setRating(book.getRating());
        return bookDto;
    }
}
