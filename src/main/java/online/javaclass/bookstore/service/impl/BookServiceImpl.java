package online.javaclass.bookstore.service.impl;

import lombok.RequiredArgsConstructor;
import online.javaclass.bookstore.data.entities.Book;
import online.javaclass.bookstore.data.repository.BookRepository;
import online.javaclass.bookstore.platform.logging.LogInvocation;
import online.javaclass.bookstore.service.BookService;
import online.javaclass.bookstore.service.dto.BookDto;
import online.javaclass.bookstore.service.exceptions.UnableToFindException;
import online.javaclass.bookstore.service.mapper.Mapper;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepo;
    private final Mapper mapper;
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
        return bookRepo.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new UnableToFindException(getFailureMessage("book.unable_to_find_id", id)));
    }

    @LogInvocation
    @Override
    public BookDto getByIsbn(String isbn) {
        return bookRepo.findByIsbn(isbn)
                .map(mapper::toDto)
                .orElseThrow(() -> new UnableToFindException(getFailureMessage("book.unable_to_find_isbn", isbn)));
    }

    @LogInvocation
    @Override
    public List<BookDto> search(String input) {
        return bookRepo.search(input)
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @LogInvocation
    @Override
    public Page<BookDto> getByAuthor(Pageable pageable, String author) {
        return bookRepo.findByAuthor(pageable, author)
                .map(mapper::toDto);
    }

    @LogInvocation
    @Override
    public Page<BookDto> getAll(Pageable pageable) {
        return bookRepo.findAll(pageable)
                .map(mapper::toDto);
    }

    private String getFailureMessage(String key, Object... objects) {
        return messageSource.getMessage(key, objects, LocaleContextHolder.getLocale());
    }
}
