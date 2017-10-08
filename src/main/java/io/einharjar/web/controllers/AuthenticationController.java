package io.einharjar.web.controllers;


import io.einharjar.domain.model.dto.TokenShallow;
import io.einharjar.domain.model.request.LoginAccountRequest;
import io.einharjar.domain.model.response.Result;
import io.einharjar.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class AuthenticationController {
    private final AuthenticationService authService;

    @Autowired
    public AuthenticationController(AuthenticationService authService) {
        this.authService = authService;
    }

    @PostMapping(value = "/authenticate", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Result<TokenShallow>> authenticate(@Valid @RequestBody LoginAccountRequest loginAccountRequest) {
        return authService.authenticate(loginAccountRequest).getResponseEntity();
    }

    @DeleteMapping(value = "/authenticate", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Result<TokenShallow>> deleteAuthtoken(@RequestHeader("Authorization") String authToken) {
        return authService.deleteAuthTokenManual(authToken).getResponseEntity();
    }
}
