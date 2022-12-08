package online.javaclass.bookstore.service;

import java.util.List;

public interface AbstractService<K, T> {
    T getById(K id);

    List<T> getAll();

    T create(T entity);

    T update(T entity);

    void deleteById(K id);
}
