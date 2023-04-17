package online.javaclass.bookstore.service.impl;

import online.javaclass.bookstore.platform.logging.LogInvocation;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@PropertySource("/application.properties")
@Service
public class StorageService {
    private final Path rootLocation = Path.of("\\Repository\\bookstore\\bookstore_pronsky\\src\\main\\resources\\static\\coverImages");

    @LogInvocation
    public void store(MultipartFile file, Long id) {
        try (InputStream inputStream = file.getInputStream()) {
            Path destinationFile = rootLocation.resolve(Paths.get(id.toString() + ".png"))
                    .normalize()
                    .toAbsolutePath();
            Files.copy(inputStream, destinationFile);
        } catch (IOException e) {
            throw new RuntimeException("unable to upload file", e);
        }
    }
}

