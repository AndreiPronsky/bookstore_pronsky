package online.javaclass.bookstore.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import online.javaclass.bookstore.MessageManager;
import online.javaclass.bookstore.exceptions.ValidationException;
import online.javaclass.bookstore.service.dto.PageableDto;
import online.javaclass.bookstore.controller.PagingUtil;
import online.javaclass.bookstore.data.entities.Book;
import online.javaclass.bookstore.data.repository.BookRepository;
import online.javaclass.bookstore.service.BookService;
import online.javaclass.bookstore.service.EntityDtoMapperService;
import online.javaclass.bookstore.service.dto.BookDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Log4j2
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepo;
    private final EntityDtoMapperService mapper;

    private final ThreadLocal<MessageManager> context = new ThreadLocal<>();
    MessageManager messageManager = context.get();

    @Override
    public Long count() {
        return bookRepo.count();
    }

    @Override
    public BookDto create(BookDto bookDto) throws ValidationException {
        log.debug("create book");
        validate(bookDto);
        Book book = mapper.toEntity(bookDto);
        Book created = bookRepo.create(book);
        return mapper.toDto(created);
    }

    @Override
    public BookDto update(BookDto bookDto) throws ValidationException {
        log.debug("update book");
        validate(bookDto);
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

    @Override
    public void validate(BookDto book) throws ValidationException {
        List<String> messages = new ArrayList<>();
        if (book.getTitle() == null || book.getTitle().isBlank()) {
            messages.add(messageManager.getMessage("error.invalid_title"));
        }
        if (book.getAuthor() == null || book.getAuthor().isBlank()) {
            messages.add(messageManager.getMessage("error.invalid_author"));
        }
        if (book.getIsbn() == null || !book.getIsbn().matches("^(?=(?:\\D*\\d){10}(?:(?:\\D*\\d){3})?$)[\\d-]+$")) {
            messages.add(messageManager.getMessage("error.invalid_isbn"));
        }
        if (book.getGenre() == null) {
            messages.add(messageManager.getMessage("error.invalid_genre"));
        }
        if (book.getCover() == null) {
            messages.add(messageManager.getMessage("error.invalid_cover"));
        }
        if (book.getRating().compareTo(BigDecimal.ZERO) < 0 ||
                book.getRating().compareTo(BigDecimal.valueOf(5)) > 0) {
            messages.add(messageManager.getMessage("error.invalid_rating"));
        }
        if (book.getPages() < 1) {
            messages.add(messageManager.getMessage("error.invalid_pages"));
        }
        if (book.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            messages.add(messageManager.getMessage("error.invalid_price"));
        }
        if (!messages.isEmpty()) {
            throw new ValidationException(messages);
        }
    }
}
