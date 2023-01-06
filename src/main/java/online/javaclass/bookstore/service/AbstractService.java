package online.javaclass.bookstore.service;

import online.javaclass.bookstore.exceptions.ValidationException;
import online.javaclass.bookstore.service.dto.PageableDto;

import java.util.List;

public interface AbstractService<K, T> {
    T getById(K id);

    List<T> getAll(PageableDto pageable);

    T create(T entity) throws ValidationException;

    T update(T entity) throws ValidationException;

    void deleteById(K id);

    Long count();

    void validate(T entity) ;
}
