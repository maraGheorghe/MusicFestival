package repository.interfaces;

import model.Entity;

import java.util.List;
import java.util.Optional;

public interface RepositoryInterface<E extends Entity> {

    Optional<E> save(E entity);

    Optional<E> find(Long ID);

    Optional<E> delete(E entity);

    Optional<E> update(E entity);

    List<E> findAll();
}
