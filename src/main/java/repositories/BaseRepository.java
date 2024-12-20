package repositories;

import java.util.List;

public interface BaseRepository<T> {
    void add(T entity);
    List<T> getAll();
    T getById(int id);
    void delete(int id);
    void update(T entity);
}

