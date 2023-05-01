package online.javaclass.bookstore.service.impl;

import lombok.RequiredArgsConstructor;
import online.javaclass.bookstore.data.entities.Book;
import online.javaclass.bookstore.data.repository.BookRepository;
import online.javaclass.bookstore.service.exceptions.UnableToFindException;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Service
@RequiredArgsConstructor
@PropertySource("/application.properties")
public class StorageServiceImpl implements online.javaclass.bookstore.service.StorageService {

    private final BookRepository bookRepo;
    private final MessageSource messageSource;
    private final Path rootLocation = Path.of("\\Repository\\bookstore\\bookstore_pronsky\\src\\main\\resources\\static\\coverImages");
    @Override
    public String store(InputStream stream, Long id) throws IOException {
        Path destinationFile = rootLocation.resolve(Paths.get(id.toString() + ".png"))
                .normalize()
                .toAbsolutePath();
        Files.copy(stream, destinationFile);
        Book book = bookRepo.findById(id).orElseThrow(() -> new UnableToFindException(messageSource.getMessage("book.unable_to_find_id",
                new Object[]{}, LocaleContextHolder.getLocale())));
        String fileName = destinationFile.toString();
        book.setFileName(fileName);
        bookRepo.save(book);
        return fileName;
    }

    @Override
    public Path get(Long id) {
        Book book = bookRepo.findById(id).orElseThrow(()
                -> new UnableToFindException(messageSource.getMessage("book.unable_to_find_id",
                new Object[]{}, LocaleContextHolder.getLocale())));
        return Path.of(book.getFileName());
    }

    private String generateFileName() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
        return LocalDateTime.now().format(formatter);
    }
}

