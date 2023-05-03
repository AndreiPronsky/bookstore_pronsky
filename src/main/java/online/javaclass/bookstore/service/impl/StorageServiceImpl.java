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
    private final Path rootLocation = Path.of("src", "main", "resources", "static", "coverImages");

    @Override
    public String store(InputStream stream, Long id) throws IOException {
        Path destinationFile = rootLocation.resolve(Paths.get(id.toString() + ".png"))
                .toAbsolutePath();
        Files.copy(stream, destinationFile);
        Book book = bookRepo.findById(id)
                .orElseThrow(() -> new UnableToFindException(getFailureMessage("book.unable_to_find_id", id)));
        String suffix = ".png";
        String prefix = "/coverImages/";
        String filePath = prefix + book.getId() + suffix;
        book.setFilePath(filePath);
        bookRepo.save(book);
        return filePath;
    }

    @Override
    public Path get(Long id) {
        Book book = bookRepo.findById(id).orElseThrow(()
                -> new UnableToFindException(getFailureMessage("book.unable_to_find_id", id)));
        return Path.of(book.getFilePath());
    }

    private String generateFileName() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
        return LocalDateTime.now().format(formatter);
    }

    private String getFailureMessage(String key, Object... objects) {
        return messageSource.getMessage(key, objects, LocaleContextHolder.getLocale());
    }
}

