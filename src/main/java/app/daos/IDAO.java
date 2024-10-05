package app.daos;

import java.util.List;

public interface IDAO<T> {
    List<T> getAll();
    T getById(Long id);
    T create(T entity);
    T update(T entity, T updateEntity);
    void delete(Long id);
}
