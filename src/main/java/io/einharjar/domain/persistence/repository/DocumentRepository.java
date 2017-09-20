package io.einharjar.domain.persistence.repository;

import io.einharjar.domain.persistence.entity.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface DocumentRepository extends CrudRepository<Document, Long>{
    Document findByName(String name);
    Document findAllByCreatedDateAfterAndCreatedDateBefore(LocalDateTime after, LocalDateTime before);
    Document findAll(Sort sort);
    Page<Document> findAll(Pageable pageable);
}
