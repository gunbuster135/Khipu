package io.einharjar.domain.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.einharjar.domain.persistence.entity.Account;
import lombok.Data;
import lombok.NonNull;

@Data
public class AccountShallow {
    @JsonProperty("account_name")
    private String userName;
    @JsonProperty("email")
    private String email;
    @JsonProperty(value = "created_at", access = JsonProperty.Access.WRITE_ONLY)
    private Long createdDate;
    @JsonProperty(value = "modified_at", access = JsonProperty.Access.WRITE_ONLY)
    private Long modifiedDate;

    public static AccountShallow from(@NonNull Account account){
        AccountShallow accountShallow = new AccountShallow();
        accountShallow.setUserName(account.getUserName());
        accountShallow.setEmail(account.getEmail());
        accountShallow.setCreatedDate(account.getCreatedDate());
        accountShallow.setModifiedDate(account.getModifiedDate());
        return accountShallow;
    }
}
