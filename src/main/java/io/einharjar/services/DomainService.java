package io.einharjar.services;

import io.einharjar.domain.TemplateEngine;
import io.einharjar.domain.persistence.entity.Document;
import io.einharjar.domain.persistence.entity.Domain;
import io.einharjar.domain.persistence.repository.DomainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DomainService {
    private final DomainRepository domainRepository;

    @Autowired
    public DomainService(DomainRepository domainRepository) {
        this.domainRepository = domainRepository;
    }

    public void createDomain(){
        Domain domain = new Domain();
        domain.setName("tuinordic");

        Document document = new Document();
        document.setDomain(domain);
        document.setTemplateEngine(TemplateEngine.Jtwig);
        document.setName("Hello world!");

        domain.addDocument(document);
        domainRepository.save(domain);
    }
}
