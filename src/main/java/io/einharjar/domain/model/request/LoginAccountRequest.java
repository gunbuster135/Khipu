package io.einharjar.domain.model.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.Optional;

@Data
public class LoginAccountRequest {
    @JsonProperty("email")
    @NotEmpty
    private String email;
    @JsonProperty("password")
    @NotEmpty
    private String password;
    @JsonProperty("keep_alive_forever")
    private Optional<Boolean> keepAliveForever;
}
