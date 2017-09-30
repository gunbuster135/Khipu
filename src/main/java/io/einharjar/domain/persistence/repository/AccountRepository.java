package io.einharjar.domain.persistence.repository;

import io.einharjar.domain.persistence.entity.Account;
import io.einharjar.domain.persistence.entity.Account;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.Optional;

/**
 * Created by omar on 2017-09-21.
 */
public interface AccountRepository extends CrudRepository<Account, Long> {
    Optional<Account> findAccountByEmail(String email);
    Optional<Account> findAccountByTokens_Value(String value);
    Iterable<Account> findByIdIn(Collection<Integer> ids, Sort sort);
    Iterable<Account> findByIdIn(Collection<Integer> ids);
}
