package io.einharjar.domain.persistence.entity;


import io.einharjar.domain.Permission;
import io.einharjar.domain.persistence.entity.common.AuditedEntity;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.*;

import static io.einharjar.utils.ObjectHelper.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "Domain")
public class Domain extends AuditedEntity {
    @Length(max = 128, min = 4)
    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "domain", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Setter(value = AccessLevel.NONE)
    private List<Document> documents = new ArrayList<>();


    @OneToMany(cascade = CascadeType.ALL)
    private Map<Permission, AccountGroup> permissions = new HashMap<>();

    public void addDocument(Document document) {
        checkNull(document, "Document cannot be null!");
        this.documents.add(document);
    }

    public void addUserPermission(@NonNull Account account, @NonNull Permission permission) {
        if (permissions.containsKey(permission)) {
            permissions.get(permission).addUser(account);
        } else {
            AccountGroup accountGroup = new AccountGroup();
            accountGroup.setAuthority(permission);
            accountGroup.addUser(account);
            permissions.put(permission, accountGroup);
        }
    }

    public boolean checkAccountPermission(@NonNull Account account, @NonNull Permission permission) {
        AccountGroup accountGroup = permissions.get(permission);
        return accountGroup != null && accountGroup.getAccounts().contains(account);
    }
}
