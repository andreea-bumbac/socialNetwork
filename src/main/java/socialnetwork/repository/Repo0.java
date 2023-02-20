package socialnetwork.repository;

import socialnetwork.domain.Entity;

import java.util.Optional;

public interface Repo0 <ID, E extends Entity<ID>>{

    Optional<E> findOne(ID id);

    Iterable<E> findAll();

    Optional<E> save(E entity);

    Optional<E> delete(ID id);

    Optional<E> update(E entity);


}