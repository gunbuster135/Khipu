package io.einharjar.domain.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.einharjar.domain.persistence.entity.Account;
import lombok.Data;

@Data
public class AccountShallow {
    @JsonProperty("account_name")
    private String userName;
    @JsonProperty("email")
    private String email;
    @JsonProperty("created_at")
    private Long createdDate;

    public static AccountShallow from(Account account){
        AccountShallow accountShallow = new AccountShallow();
        accountShallow.setUserName(account.getUserName());
        accountShallow.setEmail(account.getEmail());
        accountShallow.setCreatedDate(account.getCreatedDate());
        return accountShallow;
    }
}
