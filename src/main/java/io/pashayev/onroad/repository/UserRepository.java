package io.pashayev.onroad.repository;

import io.pashayev.onroad.domain.User;

import java.util.Collection;

public interface UserRepository<T extends User> {
    /* Nasic CRUD Operations */
    T create(T data);
    Collection<T> list(int page, int pageSize);
    T get(Long id);
    T update(T data);
    Boolean delete(Long id);
    Boolean enableUserAccount(String token);

    /* More Complex Operations */
}
