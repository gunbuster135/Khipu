package io.einharjar.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static org.springframework.util.StringUtils.*;

@Service
public class Crypt {
    private Logger log = LoggerFactory.getLogger(Crypt.class);
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public Crypt(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public String generateSalt() {
        return UUID.randomUUID().toString().substring(0, 5);
    }

    public String encrypt(String str, String salt) {
        if (isEmpty(str)) {
            log.error("String value cannot be empty when encrypting!");
            throw new IllegalArgumentException();
        }
        String valWithSalt = str.concat(salt);
        return passwordEncoder.encode(valWithSalt);
    }

    public boolean validate(String digest, String value, String salt) {
        String valWithSalt = value.concat(salt);
        return validate(digest, valWithSalt);
    }

    public boolean validate(String digest, String value) {
        return passwordEncoder.matches(digest, value);
    }
}
