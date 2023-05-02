package online.javaclass.bookstore.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

public interface StorageService {
    String store(InputStream stream, Long id) throws IOException;

    Path get(Long id);
}
