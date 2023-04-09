package online.javaclass.bookstore.service.impl;

import lombok.RequiredArgsConstructor;
import online.javaclass.bookstore.data.entities.Book;
import online.javaclass.bookstore.data.repository.BookRepository;
import online.javaclass.bookstore.platform.logging.LogInvocation;
import online.javaclass.bookstore.service.BookService;
import online.javaclass.bookstore.service.EntityDtoMapper;
import online.javaclass.bookstore.service.dto.BookDto;
import online.javaclass.bookstore.service.exceptions.UnableToFindException;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepo;
    private final EntityDtoMapper mapper;

    @Override
    public Long count() {
        return bookRepo.count();
    }

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
                .orElseThrow(() -> new UnableToFindException("book.unable_to_find_id" + " " + id)));
    }

    @LogInvocation
    @Override
    public BookDto getByIsbn(String isbn) {
        return mapper.toDto(bookRepo.findByIsbn(isbn)
                .orElseThrow(() -> new UnableToFindException("book.unable_to_find_isbn" + " " + isbn)));
    }

    @LogInvocation
    @Override
    public List<BookDto> search(String input) {
        List<BookDto> books = bookRepo.search(input)
                .stream()
                .map(mapper::toDto)
                .toList();
        if (books.isEmpty()) {
            throw new UnableToFindException("books.unable_to_find_containing" + " " + input);
        }
        return books;
    }

    @LogInvocation
    @Override
    public List<BookDto> getByAuthor(String author) {
        List<BookDto> books = bookRepo.findByAuthor(author)
                .stream()
                .map(mapper::toDto)
                .toList();
        if (books.isEmpty()) {
            throw new UnableToFindException("books.unable_to_find_author" + " " + author);
        }
        return books;
    }

    @LogInvocation
    @Override
    public List<BookDto> getAll() {
        List<BookDto> books = bookRepo.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
        if (books.isEmpty()) {
            throw new UnableToFindException("books.not_found");
        }
        return books;
    }

    @LogInvocation
    @Override
    public void deleteById(Long id) {
        bookRepo.deleteById(id);
    }
}
