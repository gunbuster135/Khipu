package io.einharjar.services;

import io.einharjar.domain.model.Result;
import io.einharjar.domain.model.Result.Status;
import io.einharjar.domain.model.dto.UserShallow;
import io.einharjar.domain.model.request.CreateUserRequest;
import io.einharjar.domain.persistence.entity.User;
import io.einharjar.domain.persistence.repository.UserRepository;
import io.einharjar.security.Crypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final Crypt crypt;

    @Autowired
    public UserService(UserRepository userRepository, Crypt crypt) {
        this.userRepository = userRepository;
        this.crypt = crypt;
    }

    public Result<UserShallow> createUser(CreateUserRequest createUserRequest) {
        User user = new User();
        user.setEmail(createUserRequest.getEmail());
        user.setUserName(createUserRequest.getUserName());
        String salt = crypt.generateSalt();
        user.setPassword(crypt.encrypt(createUserRequest.getPassword(), salt));
        user.setPasswordSalt(salt);
        Result<UserShallow> result = new Result<>();
        try {
            result.result(Optional.of(UserShallow.from(userRepository.save(user))))
                  .message("Succesfully created user!")
                  .status(Status.SUCCESFUL);
            log.info("Created following user ? ", user);
            return result;
        } catch (DataIntegrityViolationException dve) {
            log.info("Failed to create user ?", user);
            return result.message("Email already exists")
                         .status(Status.FAILED);
        }
    }

    public Result<UserShallow> getUserInfo(String token) {
        Optional<User> userOptional = userRepository.findUserByToken_Value(token);
        Optional<UserShallow> userShallowOptional = userOptional.map(user -> Optional.of(UserShallow.from(user)))
                                                                .orElse(Optional.empty());
        log.info("Retrieved following user ? with the following token ? ", userOptional.orElse(null), token);

        return userShallowOptional.map(userShallow -> new Result<UserShallow>().result(Optional.of(userShallow))
                                                                               .message("Successfully created user!")
                                                                               .status(Status.SUCCESFUL))
                                  .orElseGet(() -> new Result<UserShallow>().result(Optional.empty())
                                                                            .message("No user found with that token")
                                                                            .status(Status.FAILED));
    }
}
