package io.einharjar.web.controllers;


import io.einharjar.domain.model.response.Result;
import io.einharjar.domain.model.dto.AccountShallow;
import io.einharjar.domain.model.request.CreateAccountRequest;
import io.einharjar.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController()
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE, value = "/account")
    public ResponseEntity<String> getAccountInfo() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE, value = "/account")
    public @ResponseBody ResponseEntity<Result<AccountShallow>> createAccount(@Valid @RequestBody CreateAccountRequest createAccountRequest) {
        return accountService.createAccount(createAccountRequest).getResponseEntity();
    }
}
