package io.einharjar.domain.persistence.entity;


import io.einharjar.domain.Permission;
import io.einharjar.domain.persistence.entity.common.AuditedEntity;
import io.einharjar.utils.ObjectHelper;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.*;

import static io.einharjar.utils.ObjectHelper.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Domain extends AuditedEntity{
    @Length(max = 128 , min = 4)
    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy="domain", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval=true)
    @Setter(value = AccessLevel.NONE)
    private List<Document> documents = new ArrayList<>();

    @OneToMany(mappedBy="parentDomain", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval=true)
    @Setter(value = AccessLevel.NONE)
    private List<Domain> subDomains = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="parent_id")
    private Domain parentDomain;

    @OneToMany
    private Map<Long, UserPermissions> permissions = new HashMap<>();

    public void addDocument(Document document){
        checkNull(document, "Document cannot be null!");
        this.documents.add(document);
    }

    public void addSubDomain(Domain domain){
        checkNull(domain, "Domain cannot be null!");
        this.subDomains.add(domain);
    }

    public void addPermission(Long userId, UserPermissions userPermissions){
        if(this.permissions.containsKey(userId)){
            this.permissions.get(userId).getPermissions().addAll(userPermissions.getPermissions());
        } else {
            this.permissions.put(userId, userPermissions);
        }
    }
    public Optional<UserPermissions> getUserPermission(Long userId){
        return Optional.of(permissions.get(userId));
    }
}
