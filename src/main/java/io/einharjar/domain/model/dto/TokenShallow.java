package io.einharjar.domain.model.dto;


import io.einharjar.domain.persistence.entity.Token;
import lombok.Data;
import lombok.NonNull;

@Data
public class TokenShallow {
    private String value;
    private Long created;
    private Long validUntil;


    public static TokenShallow from(@NonNull Token t){
        TokenShallow tokenShallow = new TokenShallow();
        tokenShallow.value = t.getValue();
        tokenShallow.validUntil = t.getValidUntil();
        tokenShallow.created = t.getCreatedDate();
        return tokenShallow;
    }
}
