package io.einharjar.services;


import io.einharjar.domain.model.dto.TokenShallow;
import io.einharjar.domain.model.request.LoginAccountRequest;
import io.einharjar.domain.model.response.Meta;
import io.einharjar.domain.model.response.Result;
import io.einharjar.domain.persistence.entity.Account;
import io.einharjar.domain.persistence.entity.Token;
import io.einharjar.domain.persistence.repository.AccountRepository;
import io.einharjar.security.Crypt;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthenticationService {
    private final AccountRepository accountRepository;
    private final Long DEFAULT_TOKEN_TIME = (long) 1000 * 60 * 60 * 24 * 14; // two weeks
    private final Crypt crypt;

    @Autowired
    public AuthenticationService(AccountRepository accountRepository, Crypt crypt) {

        this.accountRepository = accountRepository;
        this.crypt = crypt;
    }

    public Result<TokenShallow> authenticate(@NonNull LoginAccountRequest loginAccountRequest) {
        Optional<Account> userOpt = accountRepository.findAccountByEmail(loginAccountRequest.getEmail());
        Result<TokenShallow> tokenShallowResult = new Result<>();
        if (!userOpt.isPresent()) {
            tokenShallowResult.result(Optional.empty())
                              .meta()
                              .message("No account exists with that email")
                              .status(Meta.Status.NOT_FOUND);
            return tokenShallowResult;
        }
        Account account = userOpt.get();
        String salt = account.getPasswordSalt();
        if (crypt.validate(account.getPassword(), loginAccountRequest.getPassword(), salt)) {
            Token t = new Token();
            t.setAccount(account);
            if (loginAccountRequest.getKeepAliveForever().isPresent() && !loginAccountRequest.getKeepAliveForever().get()) {
                t.setValidUntil(Instant.now().getEpochSecond() + DEFAULT_TOKEN_TIME);
            }
            t.setValue(UUID.randomUUID().toString());
            account.addToken(t);
            accountRepository.save(account);
            TokenShallow tokenShallow = new TokenShallow();
            tokenShallow.from(t);

            tokenShallowResult.result(Optional.of(tokenShallow))
                              .meta()
                              .status(Meta.Status.SUCCESFUL)
                              .message("Successfully authenticated!");
            return tokenShallowResult;
        }
        tokenShallowResult.result(Optional.empty())
                          .meta()
                          .message("Failed authentication, wrong password")
                          .status(Meta.Status.INVALID_AUTHENTICATION);
        return tokenShallowResult;
    }
}
