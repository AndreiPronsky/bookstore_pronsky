package online.javaclass.bookstore.data.dao;

import java.util.List;

public interface AbstractDao<K, T> {
    T findById(K id);

    List<T> findAll();

    T create(T entity);

    T update(T entity);

    boolean deleteById(K id);
}
