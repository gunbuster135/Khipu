package io.einharjar.services;

import io.einharjar.domain.model.response.Info;
import io.einharjar.domain.model.response.Result;
import io.einharjar.domain.model.dto.AccountShallow;
import io.einharjar.domain.model.request.CreateAccountRequest;
import io.einharjar.domain.persistence.entity.Account;
import io.einharjar.domain.persistence.repository.AccountRepository;
import io.einharjar.security.Crypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static io.einharjar.domain.model.response.Info.*;

@Service
public class AccountService {
    private Logger log = LoggerFactory.getLogger(AccountService.class);
    private final AccountRepository accountRepository;
    private final Crypt crypt;

    @Autowired
    public AccountService(AccountRepository accountRepository, Crypt crypt) {
        this.accountRepository = accountRepository;
        this.crypt = crypt;
    }

    public Result<AccountShallow> createAccount(CreateAccountRequest createAccountRequest) {
        Account account = new Account();
        account.setEmail(createAccountRequest.getEmail());
        account.setUserName(createAccountRequest.getUserName());
        String salt = crypt.generateSalt();
        account.setPassword(crypt.encrypt(createAccountRequest.getPassword(), salt));
        account.setPasswordSalt(salt);
        Result<AccountShallow> result = new Result<>();
        try {
            result.result(Optional.of(AccountShallow.from(accountRepository.save(account))))
                  .info().message("Succesfully created user!")
                  .status(Status.SUCCESFUL);
            log.info("Created following account {}", account);
            return result;
        } catch (DataIntegrityViolationException dve) {
            log.info("Failed to create account {} because email already exists", account);
            result.info()
                  .message("Email already exists")
                  .status(Status.FAILED);
            return result;
        }
    }

    public Result<AccountShallow> getAccountInfo(String token) {
        Optional<Account> userOptional = accountRepository.findAccountByTokens_Value(token);
        log.info("Retrieved following account {} with the following token {} ", userOptional.get(), token);
        return userOptional.map(account -> new Result<AccountShallow>().result(Optional.of(AccountShallow.from(account)))
                                                                       .info(new Info().message("Successfully created account!")
                                                                                       .status(Status.SUCCESFUL)))
                           .orElseGet(() -> new Result<AccountShallow>().result(Optional.empty())
                                                                        .info(new Info().message("No account found with that token")
                                                                                        .status(Status.FAILED)));
    }
}
