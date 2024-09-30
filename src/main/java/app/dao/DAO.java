package app.dao;

import java.util.List;

public abstract class DAO<T> implements IDAO<T> {

    @Override
    public List<T> getAll() {
        return List.of();
    }

    @Override
    public T getById(Long id) {
        return null;
    }

    @Override
    public T create(T entity) {
        return null;
    }

    @Override
    public T update(T entity, T updateEntity) {
        return null;
    }

    @Override
    public void delete(Long id) {
    }
}