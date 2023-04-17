package online.javaclass.bookstore.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AbstractService<K, T> {
    T getById(K id);

    Page<T> getAll(Pageable pageable);

    T save(T entity);
}
