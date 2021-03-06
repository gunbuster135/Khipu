package io.einharjar.domain.persistence.entity;

import io.einharjar.domain.persistence.entity.common.AuditedEntity;
import io.einharjar.utils.ObjectHelper;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data

@Table(name = "Account")
public class Account extends AuditedEntity {
    @Length(min = 4)
    private String userName;
    @NotNull
    private String password;
    @NotNull
    private String passwordSalt;
    @Length(min = 4)
    @Email
    @Column(unique = true)
    private String email;
    private Long timestampRecentAuthentication;
    @OneToMany(mappedBy="account", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval=true)
    @Setter(value = AccessLevel.NONE)
    private Set<Token> tokens = new HashSet<>();

    private Long tokenValidity;

    public void addToken(Token token){
        ObjectHelper.checkNull(token, "Token cannot be null!");
        this.tokens.add(token);
    }
}
