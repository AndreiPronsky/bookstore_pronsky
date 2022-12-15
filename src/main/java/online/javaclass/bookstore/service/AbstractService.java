package online.javaclass.bookstore.service;

import online.javaclass.bookstore.controller.Pageable;

import java.util.List;

public interface AbstractService<K, T> {
    T getById(K id);

    List<T> getAll();

    List<T> getAll(Pageable pageable);

    T create(T entity);

    T update(T entity);

    void deleteById(K id);
}
