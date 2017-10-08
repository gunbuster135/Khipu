package io.einharjar.domain.persistence.repository;


import io.einharjar.domain.persistence.entity.Token;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface TokenRepository  extends CrudRepository<Token, Long>{

    Optional<Token> findByValue(String value);
    List<Token>     findAllByValidUntilBefore(Long validUntilBefore);
}
