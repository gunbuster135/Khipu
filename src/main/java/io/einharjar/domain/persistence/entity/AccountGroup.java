package io.einharjar.domain.persistence.entity;


import io.einharjar.domain.Permission;
import io.einharjar.domain.persistence.entity.common.AuditedEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "AccountGroup")
@Data
public class AccountGroup extends AuditedEntity {
    @Enumerated(EnumType.STRING)
    private Permission authority;
    @ManyToMany(targetEntity = Account.class,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "account_group_people",
            joinColumns =
            @JoinColumn(name = "permission_role_id"),
            inverseJoinColumns =
            @JoinColumn(name = "account_id"))
    @Setter(value = AccessLevel.NONE)
    private List<Account> accounts = new ArrayList<>();

    public void addUser(@NonNull Account account){
        this.accounts.add(account);
    }

    public void addUsers(@NonNull List<Account> accounts){
        this.accounts.addAll(accounts);
    }
}
