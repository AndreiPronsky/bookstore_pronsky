package online.javaclass.bookstore.data.dao;

import java.util.List;

public interface AbstractDao<K, T> {
    T getById(K id);

    List<T> getAll();

    List<T> getAll(int limit, int offset);

    T create(T entity);

    T update(T entity);

    boolean deleteById(K id);
}
