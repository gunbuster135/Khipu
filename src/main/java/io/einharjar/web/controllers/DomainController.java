package io.einharjar.web.controllers;


import io.einharjar.services.DomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/domain")
public class DomainController {
    private final DomainService domainService;

    @Autowired
    public DomainController(DomainService domainService) {
        this.domainService = domainService;
    }

    @PostMapping()
    public ResponseEntity<String> createDomain(){
        domainService.createDomain();
        return new ResponseEntity<String>(HttpStatus.OK);
    }

}
