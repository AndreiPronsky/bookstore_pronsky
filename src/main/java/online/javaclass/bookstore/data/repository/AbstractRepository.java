package online.javaclass.bookstore.data.repository;

import java.util.List;

public interface AbstractRepository<K, T> {
    T getById(K id);

    List<T> getAll();

    List<T> getAll(int limit, int offset);

    T create(T entity);

    T update(T entity);

    boolean deleteById(K id);

    Long count();
}
