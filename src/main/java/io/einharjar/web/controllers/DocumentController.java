package io.einharjar.web.controllers;


import io.einharjar.domain.persistence.entity.Document;
import io.einharjar.services.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/document")
public class DocumentController {
    @Autowired
    DocumentService documentService;

    @GetMapping()
    public ResponseEntity<Document> getDocument(){
        Document document = documentService.createDocument();
        return new ResponseEntity<>(document, HttpStatus.OK);
    }
}
