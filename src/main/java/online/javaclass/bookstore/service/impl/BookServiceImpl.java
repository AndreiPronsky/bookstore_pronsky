package online.javaclass.bookstore.service.impl;

import lombok.RequiredArgsConstructor;
import online.javaclass.bookstore.controller.PagingUtil;
import online.javaclass.bookstore.data.entities.Book;
import online.javaclass.bookstore.data.repository.BookRepository;
import online.javaclass.bookstore.exceptions.UnableToDeleteException;
import online.javaclass.bookstore.exceptions.UnableToFindException;
import online.javaclass.bookstore.platform.MessageManager;
import online.javaclass.bookstore.platform.logging.LogInvocation;
import online.javaclass.bookstore.service.BookService;
import online.javaclass.bookstore.service.EntityDtoMapperService;
import online.javaclass.bookstore.service.dto.BookDto;
import online.javaclass.bookstore.service.dto.PageableDto;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepo;
    private final EntityDtoMapperService mapper;
    private final MessageManager messageManager;
    private final PagingUtil pagingUtil;

    /**
     * Invokes count method of a repository layer
     *
     * @return Long value of number of items in a database
     */
    @Override
    public Long count() {
        return bookRepo.count();
    }

    /**
     * Takes input parameters from data transfer object, maps it to entity and invokes a create method of a repository
     * layer.
     *
     * @param bookDto is a data transfer object got from client side to be created.
     * @return Object returned from repository layer mapped to data transfer object.
     */
    @LogInvocation
    @Override
    public BookDto create(BookDto bookDto) {
        Book book = mapper.toEntity(bookDto);
        Book created = bookRepo.create(book);
        return mapper.toDto(created);
    }

    /**
     * Takes input parameters from data transfer object, maps it to entity and invokes an update method of a repository
     * layer.
     *
     * @param bookDto is a data transfer object.
     * @return Object returned from repository layer mapped to data transfer object.
     */
    @LogInvocation
    @Override
    public BookDto update(BookDto bookDto) {
        Book book = mapper.toEntity(bookDto);
        Book updated = bookRepo.update(book);
        return mapper.toDto(updated);
    }

    /**
     * Takes input parameter and invokes getById method of a repository layer.
     *
     * @param id Long value of book id to find existing book.
     * @return Object returned from repository layer mapped to data transfer object.
     * @throws UnableToFindException if id doesn't correspond to any database item.
     */
    @LogInvocation
    @Override
    public BookDto getById(Long id) {
        Book book = bookRepo.findById(id);
        if (book == null) {
            throw new UnableToFindException(messageManager.getMessage("book.unable_to_find_id") + " " + id);
        } else {
            return mapper.toDto(book);
        }
    }

    /**
     * Takes input parameter and invokes getByIsbn method of a repository layer.
     *
     * @param isbn String value of book isbn to find existing book.
     * @return Object returned from repository layer mapped to data transfer object.
     * @throws UnableToFindException if isbn doesn't correspond to any database item.
     */
    @LogInvocation
    @Override
    public BookDto getByIsbn(String isbn) {
        Book book = bookRepo.findByIsbn(isbn);
        if (book == null) {
            throw new UnableToFindException(messageManager.getMessage("book.unable_to_find_isbn") + " " + isbn);
        } else {
            return mapper.toDto(book);
        }
    }

    /**
     * Takes input parameter and invokes search method of a repository layer.
     *
     * @param input String value of complete or partial value of a book title or author.
     * @return List of objects returned from repository layer mapped to data transfer objects.
     * @throws UnableToFindException if input doesn't correspond to any database item.
     */
    @LogInvocation
    @Override
    public List<BookDto> search(String input) {
        List<BookDto> books = bookRepo.search(input).stream()
                .map(mapper::toDto)
                .toList();
        if (books.isEmpty()) {
            throw new UnableToFindException(messageManager.getMessage("books.unable_to_find_containing") + " " + input);
        } else {
            return books;
        }
    }

    /**
     * Takes input parameter and invokes getByAuthor method of a repository layer using extracted from
     * PageableDto object values of limit and offset.
     *
     * @param author   String value of complete value of a book author.
     * @param pageable PageableDto object containing information about the current page size and page number
     *                 on client side.
     * @return List of objects returned from repository layer mapped to data transfer objects.
     * @throws UnableToFindException if input doesn't correspond to any database item
     */
    @LogInvocation
    @Override
    public List<BookDto> getByAuthor(String author, PageableDto pageable) {
        List<BookDto> books = bookRepo.findByAuthor(author, pageable.getLimit(), pageable.getOffset()).stream()
                .map(mapper::toDto)
                .toList();
        if (books.isEmpty()) {
            throw new UnableToFindException(messageManager.getMessage("books.unable_to_find_author") + " " + author);
        } else {
            return books;
        }
    }

    /**
     * Invokes getAll method of a repository layer using extracted from
     * PageableDto object values of limit and offset.
     *
     * @param pageable PageableDto object containing information about the current page size and page number
     *                 on client side.
     * @return List of objects returned from repository layer mapped to data transfer objects.
     * @throws UnableToFindException if something went wrong during an attempt to get items from database.
     */
    @LogInvocation
    @Override
    public List<BookDto> getAll(PageableDto pageable) {
        Long totalItems = bookRepo.count();
        Long totalPages = pagingUtil.getTotalPages(totalItems, pageable);
        pageable.setTotalItems(bookRepo.count());
        pageable.setTotalPages(totalPages);
        if (pageable.getPage() > totalPages) {
            pageable.setPage(1);
        }
        List<BookDto> books = bookRepo.findAll(pageable.getLimit(), pageable.getOffset()).stream()
                .map(mapper::toDto)
                .toList();
        if (books.isEmpty()) {
            throw new UnableToFindException(messageManager.getMessage("books.not_found"));
        } else {
            return books;
        }
    }

    /**
     * Takes input parameter and invokes deleteById method of a repository layer.
     *
     * @param id Long value of book id to delete existing book.
     * @throws UnableToFindException if id doesn't correspond to any database item.
     */
    @LogInvocation
    @Override
    public void deleteById(Long id) {
        boolean deleted = bookRepo.deleteById(id);
        if (!deleted) {
            throw new UnableToDeleteException(messageManager.getMessage("book.unable_to_delete_id") + " " + id);
        }
    }
}
