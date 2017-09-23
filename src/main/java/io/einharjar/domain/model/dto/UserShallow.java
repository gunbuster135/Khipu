package io.einharjar.domain.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.einharjar.domain.persistence.entity.User;
import lombok.Data;

@Data
public class UserShallow {
    @JsonProperty("user_name")
    private String userName;
    @JsonProperty("email")
    private String email;
    @JsonProperty("created_at")
    private Long createdDate;

    public static UserShallow from(User user){
        UserShallow userShallow = new UserShallow();
        userShallow.setUserName(user.getUserName());
        userShallow.setEmail(user.getEmail());
        userShallow.setCreatedDate(user.getCreatedDate());
        return userShallow;
    }
}
