package com.example.minitest.common;

import java.util.List;
import java.util.Optional;

public interface ICRUDService<E> {
    List<E> findAll();

    Optional<E> findById(Long id);

    E save(E e);

    void delete (Long id);
}
