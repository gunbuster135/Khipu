package io.einharjar.domain.model.dto;


import io.einharjar.domain.persistence.entity.Token;
import lombok.Data;
import lombok.NonNull;

@Data
public class TokenShallow {
    private String value;
    private Long created;
    private Long validUntil;



    public void from(@NonNull Token t){
        this.value = t.getValue();
        this.validUntil = t.getValidUntil();
        this.created = t.getCreatedDate();
    }
}
