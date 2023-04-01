package online.javaclass.bookstore.service.impl;

import lombok.RequiredArgsConstructor;
import online.javaclass.bookstore.controller.utils.PagingUtil;
import online.javaclass.bookstore.data.entities.Book;
import online.javaclass.bookstore.data.repository.BookRepository;
import online.javaclass.bookstore.exceptions.UnableToCreateException;
import online.javaclass.bookstore.exceptions.UnableToFindException;
import online.javaclass.bookstore.exceptions.UnableToUpdateException;
import online.javaclass.bookstore.platform.MessageManager;
import online.javaclass.bookstore.platform.logging.LogInvocation;
import online.javaclass.bookstore.service.BookService;
import online.javaclass.bookstore.service.EntityDtoMapper;
import online.javaclass.bookstore.service.dto.BookDto;
import online.javaclass.bookstore.service.dto.PageableDto;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepo;
    private final EntityDtoMapper mapper;
    private final MessageManager messageManager;
    private final PagingUtil pagingUtil;

    @Override
    public Long count() {
        return bookRepo.count();
    }

    @LogInvocation
    @Override
    public BookDto create(BookDto bookDto) {
        Book book = mapper.toEntity(bookDto);
        try {
            Book created = bookRepo.create(book);
            return mapper.toDto(created);
        } catch (EntityExistsException e) {
            throw new UnableToCreateException(messageManager.getMessage("book.unable_to_create"));
        }
    }

    @LogInvocation
    @Override
    public BookDto update(BookDto bookDto) {
        Book book = mapper.toEntity(bookDto);
        try {
            Book updated = bookRepo.update(book);
            return mapper.toDto(updated);
        } catch (IllegalArgumentException e) {
            throw new UnableToUpdateException(messageManager.getMessage("book.unable_to_update"));
        }
    }

    @LogInvocation
    @Override
    public BookDto getById(Long id) {
        Book book = bookRepo.findById(id);
        if (book == null) {
            throw new UnableToFindException("book.unable_to_find_id" + " " + id);
//            throw new UnableToFindException(messageManager.getMessage("book.unable_to_find_id") + " " + id);
        }
        return mapper.toDto(book);
    }

    @LogInvocation
    @Override
    public BookDto getByIsbn(String isbn) {
        Book book = bookRepo.findByIsbn(isbn);
        if (book == null) {
            throw new UnableToFindException(messageManager.getMessage("book.unable_to_find_isbn") + " " + isbn);
        }
        return mapper.toDto(book);
    }

    @LogInvocation
    @Override
    public List<BookDto> search(String input) {
        List<BookDto> books = bookRepo.search(input).stream()
                .map(mapper::toDto)
                .toList();
        if (books.isEmpty()) {
            throw new UnableToFindException(messageManager.getMessage("books.unable_to_find_containing") + " " + input);
        }
        return books;
    }

    @LogInvocation
    @Override
    public List<BookDto> getByAuthor(String author, PageableDto pageable) {
        List<BookDto> books = bookRepo.findByAuthor(author, pageable.getLimit(), pageable.getOffset()).stream()
                .map(mapper::toDto)
                .toList();
        if (books.isEmpty()) {
            throw new UnableToFindException(messageManager.getMessage("books.unable_to_find_author") + " " + author);
        }
        return books;
    }

    @LogInvocation
    @Override
    public List<BookDto> getAll(PageableDto pageable) {
        Long totalItems = bookRepo.count();
        Long totalPages = pagingUtil.getTotalPages(totalItems, pageable);
        pageable.setTotalItems(totalItems);
        pageable.setTotalPages(totalPages);
        List<BookDto> books = bookRepo.findAll(pageable.getLimit(), pageable.getOffset()).stream()
                .map(mapper::toDto)
                .toList();
        if (books.isEmpty()) {
            throw new UnableToFindException(messageManager.getMessage("books.not_found"));
        }
        return books;
    }

    @LogInvocation
    @Override
    public void deleteById(Long id) {
        bookRepo.deleteById(id);
    }
}
