package io.einharjar.domain.persistence.repository;

import io.einharjar.domain.persistence.entity.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.Optional;

/**
 * Created by omar on 2017-09-21.
 */
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findUserByEmail(String email);
    Optional<User> findUserByToken_Value(String value);
    Iterable<User> findByIdIn(Collection<Integer> ids, Sort sort);
    Iterable<User> findByIdIn(Collection<Integer> ids);
}
