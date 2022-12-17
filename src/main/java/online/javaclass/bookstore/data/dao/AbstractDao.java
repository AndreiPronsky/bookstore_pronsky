package online.javaclass.bookstore.data.dao;

import java.util.List;

public interface AbstractDao<K, T> {
    T findById(K id);

    List<T> findAll();

    List<T> findAll(int limit, int offset);

    T create(T entity);

    T update(T entity);

    boolean deleteById(K id);
}
