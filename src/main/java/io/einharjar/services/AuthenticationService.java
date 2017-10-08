package io.einharjar.services;


import com.github.benmanes.caffeine.cache.Cache;
import io.einharjar.domain.Permission;
import io.einharjar.domain.model.dto.TokenShallow;
import io.einharjar.domain.model.request.LoginAccountRequest;
import io.einharjar.domain.model.response.Info;
import io.einharjar.domain.model.response.Result;
import io.einharjar.domain.persistence.entity.*;
import io.einharjar.domain.persistence.repository.AccountRepository;
import io.einharjar.domain.persistence.repository.TokenRepository;
import io.einharjar.security.Crypt;
import lombok.Data;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.naming.AuthenticationException;
import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthenticationService {
    private Logger log = LoggerFactory.getLogger(AuthenticationService.class);
    private final TokenRepository tokenRepository;
    private final AccountRepository accountRepository;
    private final Long DEFAULT_TOKEN_TIME = (long) 1000 * 60 * 60 * 24 * 14; // two weeks
    private final Crypt crypt;
    private final Cache<String, Pair<Account, Token>> authCache;

    @Autowired
    public AuthenticationService(AccountRepository accountRepository, Crypt crypt, TokenRepository tokenRepository, Cache<String, Pair<Account, Token>> authCache) {
        this.accountRepository = accountRepository;
        this.crypt = crypt;
        this.tokenRepository = tokenRepository;
        this.authCache = authCache;
    }

    /**
     * Will create a token if authentication successful and persist it in DB and
     * return a shallow token instance. If authentication fails, return empty Result
     *
     * @param loginAccountRequest
     * @return
     */
    public Result<TokenShallow> authenticate(@NonNull LoginAccountRequest loginAccountRequest) {
        Optional<Account> userOpt = accountRepository.findAccountByEmail(loginAccountRequest.getEmail());
        Result<TokenShallow> tokenShallowResult = new Result<>();
        if (!userOpt.isPresent()) {
            tokenShallowResult.result(Optional.empty())
                              .info()
                              .message("No account exists with that email")
                              .status(Info.Status.NOT_FOUND);
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
            TokenShallow tokenShallow = TokenShallow.from(t);
            tokenShallowResult.result(Optional.of(tokenShallow))
                              .info()
                              .status(Info.Status.SUCCESFUL)
                              .message("Successfully authenticated!");
            authCache.put(t.getValue(), Pair.of(account, t)); //Cache token
            return tokenShallowResult;
        }
        tokenShallowResult.result(Optional.empty())
                          .info()
                          .message("Failed authentication, wrong password")
                          .status(Info.Status.INVALID_AUTHENTICATION);
        return tokenShallowResult;
    }

    /**
     * Delete an authentication token manually (e.g user wants to make sure he does not stay logged in after leaving page)
     * Will both delete
     *
     * @param authToken
     * @return
     */
    public Result<TokenShallow> deleteAuthTokenManual(@NonNull String authToken) {
        Optional<Token> tokenOpt = tokenRepository.findByValue(authToken);
        final Result<TokenShallow> tokenShallowResult = new Result<>();
        return tokenOpt.map(token -> {
            authCache.invalidate(authToken);
            tokenRepository.delete(token);
            return tokenShallowResult.result(Optional.of(TokenShallow.from(token)))
                                     .info(new Info().status(Info.Status.SUCCESFUL)
                                                     .message("Succesfully deleted token!"));
        })
                       .orElseGet(() -> tokenShallowResult.result(Optional.empty())
                                                          .info(new Info().status(Info.Status.FAILED)
                                                                          .message("No token found with that value")));
    }


    public void checkPermission(@NonNull Domain domain, @NonNull Permission permission, @NonNull String token) throws AuthenticationException {
        boolean success = checkDomainPermission(domain, permission, token);
        if (!success) {
            throw new AuthenticationException();
        }
    }


    public void checkPermission(@NonNull Document domain, @NonNull Permission permission, @NonNull String token) throws AuthenticationException {
        boolean success = checkDocumentPermission(domain, permission, token);
        if (!success) {
            throw new AuthenticationException();
        }
    }

    /**
     * Check if the following authentication token connected to a user has the following permission in the domain
     *
     * @param domain
     * @param permission
     * @param token
     * @return
     */
    private boolean checkDomainPermission(@NonNull Domain domain, @NonNull Permission permission, @NonNull String token) {
        Optional<Account> optionalAccount = findUserWithToken(token);
        if (!optionalAccount.isPresent()) return false;
        Account account = optionalAccount.get();
        return (domain.checkAccountPermission(account, permission) || domain.checkAccountPermission(account, Permission.SUPER_USER));
    }

    private boolean checkDocumentPermission(@NonNull Document document, @NonNull Permission permission, @NonNull String token) {
        Optional<Account> optionalAccount = findUserWithToken(token);
        if (!optionalAccount.isPresent()) return false;
        Account account = optionalAccount.get();
        Domain domain = document.getDomain();
        return (domain.checkAccountPermission(account, permission) || domain.checkAccountPermission(account, Permission.SUPER_USER));
    }

    private Optional<Account> findUserWithToken(@NonNull String token) {
        Optional<Pair<Account, Token>> accountTokenPairOpt = Optional.ofNullable(authCache.getIfPresent(token));
        if (accountTokenPairOpt.isPresent()) {
            if (accountTokenPairOpt.get().getSecond().getValidUntil() != null &&
                    accountTokenPairOpt.get().getSecond().getValidUntil() < Instant.now().getEpochSecond()) {
                authCache.invalidate(token);
                log.info("Invalidated following token in cache after validity timestamp has passed: {}", token);
                return Optional.empty();
            }
            log.info("Found following token in cache: {} ", token);
            return Optional.of(accountTokenPairOpt.get().getFirst());
        }
        return accountRepository.findAccountByTokens_Value(token);
    }

}
