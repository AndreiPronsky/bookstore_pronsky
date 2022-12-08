package online.javaclass.bookstore.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import online.javaclass.bookstore.service.EntityDtoMapperService;
import online.javaclass.bookstore.data.entities.Book;
import online.javaclass.bookstore.data.repository.BookRepository;
import online.javaclass.bookstore.service.BookService;
import online.javaclass.bookstore.service.dto.BookDto;

import java.util.List;

@RequiredArgsConstructor
@Log4j2
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepo;
    private final EntityDtoMapperService mapper;

    @Override
    public BookDto create(BookDto bookDto) {
        log.debug("create book");
        Book book = mapper.toEntity(bookDto);
        Book created = bookRepo.create(book);
        return mapper.toDto(created);
    }

    @Override
    public BookDto update(BookDto bookDto) {
        log.debug("update book");
        Book book = mapper.toEntity(bookDto);
        Book updated = bookRepo.update(book);
        return mapper.toDto(updated);
    }

    @Override
    public BookDto getById(Long id) {
        log.debug("get book by id");
        Book book = bookRepo.findById(id);
        if (book == null) {
            throw new RuntimeException("Book with id " + id + " not found!");
        }
        return mapper.toDto(book);
    }

    @Override
    public BookDto getByIsbn(String isbn) {
        log.debug("get book by isbn");
        Book book = bookRepo.findByIsbn(isbn);
        return mapper.toDto(book);
    }

    @Override
    public List<BookDto> getByAuthor(String author) {
        log.debug("get books by author");
        return bookRepo.findByAuthor(author).stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public List<BookDto> getAll() {
        log.debug("get all books");
        return bookRepo.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        log.debug("delete book by id");
        boolean deleted = bookRepo.deleteById(id);
        if (!deleted) {
            log.error("Unable to delete book with id : " + id);
        }
    }
}
