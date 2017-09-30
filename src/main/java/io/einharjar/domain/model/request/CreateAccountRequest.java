package io.einharjar.domain.model.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

@Data
public class CreateAccountRequest {
    @JsonProperty("account_name")
    @Length(min = 4)
    private String userName;
    @JsonProperty("email")
    @Length(min = 8)
    @Email
    private String email;
    @JsonProperty("password")
    @Length(min = 8)
    private String password;
}
