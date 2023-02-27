package online.javaclass.bookstore.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import online.javaclass.bookstore.MessageManager;
import online.javaclass.bookstore.controller.PagingUtil;
import online.javaclass.bookstore.data.entities.Book;
import online.javaclass.bookstore.data.repository.BookRepository;
import online.javaclass.bookstore.exceptions.UnableToDeleteException;
import online.javaclass.bookstore.exceptions.UnableToFindException;
import online.javaclass.bookstore.service.BookService;
import online.javaclass.bookstore.service.EntityDtoMapperService;
import online.javaclass.bookstore.service.dto.BookDto;
import online.javaclass.bookstore.service.dto.PageableDto;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Log4j2
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepo;
    private final EntityDtoMapperService mapper;
    private final MessageManager messageManager;

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
        if (book == null) {
            throw new UnableToFindException(messageManager.getMessage("book.unable_to_find_id") + " " + id);
        } else {
            return mapper.toDto(book);
        }
    }

    @Override
    public BookDto getByIsbn(String isbn) {
        log.debug("get book by isbn");
        Book book = bookRepo.getByIsbn(isbn);
        if (book == null) {
            throw new UnableToFindException(messageManager.getMessage("book.unable_to_find_isbn") + " " + isbn);
        } else {
            return mapper.toDto(book);
        }
    }

    @Override
    public List<BookDto> search(String input) {
        log.debug("search by " + input);
        List<BookDto> books = bookRepo.search(input).stream()
                .map(mapper::toDto)
                .toList();
        if (books.isEmpty()) {
            throw new UnableToFindException(messageManager.getMessage("books.unable_to_find_containing") + " " + input);
        } else {
            return books;
        }
    }

    @Override
    public List<BookDto> getByAuthor(String author, PageableDto pageable) {
        log.debug("get books by author");
        List<BookDto> books = bookRepo.getByAuthor(author, pageable.getLimit(), pageable.getOffset()).stream()
                .map(mapper::toDto)
                .toList();
        if (books.isEmpty()) {
            throw new UnableToFindException(messageManager.getMessage("books.unable_to_find_author") + " " + author);
        } else {
            return books;
        }
    }

    @Override
    public List<BookDto> getAll(PageableDto pageable) {
        log.debug("get all books");
        List<BookDto> books = bookRepo.getAll(pageable.getLimit(), pageable.getOffset()).stream()
                .map(mapper::toDto)
                .toList();
        if (books.isEmpty()) {
            throw new UnableToFindException(messageManager.getMessage("books.not_found"));
        } else {
            Long totalItems = bookRepo.count();
            Long totalPages = PagingUtil.getTotalPages(totalItems, pageable);
            pageable.setTotalItems(bookRepo.count());
            pageable.setTotalPages(totalPages);
            return books;
        }
    }

    @Override
    public void deleteById(Long id) {
        log.debug("delete book by id");
        boolean deleted = bookRepo.deleteById(id);
        if (!deleted) {
            log.error("Unable to delete book with id : " + id);
            throw new UnableToDeleteException(messageManager.getMessage("book.unable_to_delete_id") + " " + id);
        }
    }
}
