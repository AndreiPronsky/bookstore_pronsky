package online.javaclass.bookstore.service.impl;

import online.javaclass.bookstore.data.dao.BookDao;
import online.javaclass.bookstore.data.entities.Book;
import online.javaclass.bookstore.service.BookService;
import online.javaclass.bookstore.service.dto.BookDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class BookServiceImpl implements BookService {

    private final BookDao bookDao;
    private static final Logger log = LogManager.getLogger();

    public BookServiceImpl(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Override
    public BookDto create(BookDto bookDto) {
        log.debug("create book");
        Book book = toEntity(bookDto);
        Book created = bookDao.create(book);
        return toDto(created);
    }

    @Override
    public BookDto update(BookDto bookDto) {
        log.debug("update book");
        Book book = toEntity(bookDto);
        Book updated = bookDao.update(book);
        return toDto(updated);
    }

    @Override
    public BookDto getById(Long id) {
        log.debug("get book by id");
        Book book = bookDao.findById(id);
        return toDto(book);
    }

    @Override
    public BookDto getByIsbn(String isbn) {
        log.debug("get book by isbn");
        Book book = bookDao.findByIsbn(isbn);
        return toDto(book);
    }

    @Override
    public List<BookDto> getByAuthor(String author) {
        log.debug("get books by author");
        return bookDao.findByAuthor(author).stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public List<BookDto> getAll() {
        log.debug("get all books");
        return bookDao.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        log.debug("delete book by id");
        boolean deleted = bookDao.deleteById(id);
        if (!deleted) {
            log.error("Unable to delete book with id : " + id);
        }
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
