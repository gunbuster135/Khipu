package io.einharjar.domain.persistence.entity;


import io.einharjar.domain.Permission;
import io.einharjar.domain.persistence.entity.common.AuditedEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Domain extends AuditedEntity{
    @Length(max = 128 , min = 8)
    @Column(nullable = false)
    private String name;
    @OneToMany(mappedBy="domain", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval=true)
    private List<Document> documents;
    @OneToMany(mappedBy="parentDomain", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval=true)
    private List<Domain> subDomains;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="parent_id")
    private Domain parentDomain;
    @OneToMany
    private Map<User, UserPermissions> permissions;
}
