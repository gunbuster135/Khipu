package io.einharjar.domain.persistence.repository;

import io.einharjar.domain.persistence.entity.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface DocumentRepository extends CrudRepository<Document, Long>{
    Optional<Document> findByName(String name);
    Optional<Document> findAllByCreatedDateAfterAndCreatedDateBefore(LocalDateTime after, LocalDateTime before);
    Optional<Document> findAll(Sort sort);
    Page<Document> findAll(Pageable pageable);
}
