package repository;

import model.Entity;

import java.util.List;
import java.util.Optional;

public interface RepositoryInterface<E extends Entity> {

    Optional<E> save(E entity);

    Optional<E> find(Long ID);

    Optional<E> delete(Long ID);

    Optional<E> update(E entity);

    List<E> findAll();
}
