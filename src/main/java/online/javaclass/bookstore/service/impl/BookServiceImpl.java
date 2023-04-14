package online.javaclass.bookstore.service.impl;

import lombok.RequiredArgsConstructor;
import online.javaclass.bookstore.data.entities.Book;
import online.javaclass.bookstore.data.repository.BookRepository;
import online.javaclass.bookstore.platform.logging.LogInvocation;
import online.javaclass.bookstore.service.BookService;
import online.javaclass.bookstore.service.EntityDtoMapper;
import online.javaclass.bookstore.service.dto.BookDto;
import online.javaclass.bookstore.service.exceptions.UnableToFindException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepo;
    private final EntityDtoMapper mapper;
    private final MessageSource messageSource;

    @LogInvocation
    @Override
    public BookDto save(BookDto bookDto) {
        Book book = mapper.toEntity(bookDto);
        return mapper.toDto(bookRepo.save(book));
    }

    @LogInvocation
    @Override
    public BookDto getById(Long id) {
        return mapper.toDto(bookRepo.findById(id)
                .orElseThrow(() -> new UnableToFindException(getFailureMessage("book.unable_to_find_id") + " " + id)));
    }

    @LogInvocation
    @Override
    public BookDto getByIsbn(String isbn) {
        return mapper.toDto(bookRepo.findByIsbn(isbn)
                .orElseThrow(() -> new UnableToFindException(getFailureMessage("book.unable_to_find_isbn") + " " + isbn)));
    }

    @LogInvocation
    @Override
    public List<BookDto> search(String input) {
        List<BookDto> books = bookRepo.search(input)
                .stream()
                .map(mapper::toDto)
                .toList();
        if (books.isEmpty()) {
            throw new UnableToFindException(getFailureMessage("books.unable_to_find_containing") + " " + input);
        }
        return books;
    }

    @LogInvocation
    @Override
    public Page<BookDto> getByAuthor(Pageable pageable, String author) {
        Page<BookDto> books = bookRepo.findByAuthor(pageable, author).map(mapper::toDto);
        if (books.isEmpty()) {
            throw new UnableToFindException(getFailureMessage("books.unable_to_find_author") + " " + author);
        }
        return books;
    }

    @LogInvocation
    @Override
    public Page<BookDto> getAll(Pageable pageable) {
        Page<BookDto> books = bookRepo.findAll(pageable)
                .map(mapper::toDto);
        if (books.isEmpty()) {
            throw new UnableToFindException(getFailureMessage("books.not_found"));
        }
        return books;
    }

    @LogInvocation
    @Override
    public void deleteById(Long id) {
        bookRepo.deleteById(id);
    }

    private String getFailureMessage(String key) {
        return messageSource.getMessage(key, new Object[]{}, LocaleContextHolder.getLocale());
    }
}
