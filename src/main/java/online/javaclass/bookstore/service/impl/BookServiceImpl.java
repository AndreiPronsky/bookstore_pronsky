package online.javaclass.bookstore.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import online.javaclass.bookstore.service.dto.PageableDto;
import online.javaclass.bookstore.controller.PagingUtil;
import online.javaclass.bookstore.data.entities.Book;
import online.javaclass.bookstore.data.repository.BookRepository;
import online.javaclass.bookstore.service.BookService;
import online.javaclass.bookstore.service.EntityDtoMapperService;
import online.javaclass.bookstore.service.dto.BookDto;

import java.util.List;

@RequiredArgsConstructor
@Log4j2
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepo;
    private final EntityDtoMapperService mapper;

    @Override
    public Long count() {
        return bookRepo.count();
    }

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
        Book book = bookRepo.getById(id);
        return mapper.toDto(book);
    }

    @Override
    public BookDto getByIsbn(String isbn) {
        log.debug("get book by isbn");
        Book book = bookRepo.getByIsbn(isbn);
        return mapper.toDto(book);
    }

    @Override
    public List<BookDto> getByAuthor(String author) {
        log.debug("get books by author");
        return bookRepo.getByAuthor(author).stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public List<BookDto> search(String input) {
        log.debug("search by " + input);
        return bookRepo.search(input).stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public List<BookDto> getByAuthor(String author, PageableDto pageable) {
        log.debug("get books by author");
        return bookRepo.getByAuthor(author, pageable.getLimit(), pageable.getOffset()).stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public List<BookDto> getAll() {
        log.debug("get all books");
        return bookRepo.getAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public List<BookDto> getAll(PageableDto pageable) {
        log.debug("get all books");
        List<BookDto> books = bookRepo.getAll(pageable.getLimit(), pageable.getOffset()).stream()
                .map(mapper::toDto)
                .toList();
        Long totalItems = bookRepo.count();
        Long totalPages = PagingUtil.getTotalPages(totalItems, pageable);
        pageable.setTotalItems(bookRepo.count());
        pageable.setTotalPages(totalPages);
        return books;
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
