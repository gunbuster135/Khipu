package io.einharjar.services;


import io.einharjar.domain.persistence.entity.Document;
import io.einharjar.domain.persistence.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DocumentService {
    private final DocumentRepository documentRepository;

    @Autowired
    public DocumentService(DocumentRepository documentRepository){
        this.documentRepository = documentRepository;
    }

    public Document createDocument(){
        Document document = new Document();
        document.setName("test22ingtes4ting");
        documentRepository.save(document);
        return document;
    }
}
