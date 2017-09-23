package io.einharjar.web.controllers;


import io.einharjar.domain.model.Result;
import io.einharjar.domain.model.dto.UserShallow;
import io.einharjar.domain.model.request.CreateUserRequest;
import io.einharjar.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getUserInfo() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Result<UserShallow>> createUser(@Validated CreateUserRequest createUserRequest) {
        Result<UserShallow> userShallowResult = userService.createUser(createUserRequest);
        return new ResponseEntity<>(userShallowResult, userShallowResult.status().getHttpStatus());
    }
}
