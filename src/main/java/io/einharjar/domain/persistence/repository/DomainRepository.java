package io.einharjar.domain.persistence.repository;

import io.einharjar.domain.persistence.entity.Domain;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DomainRepository extends CrudRepository<Domain, Long> {
    
}
