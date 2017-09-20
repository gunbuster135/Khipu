package io.einharjar.domain.persistence.entity;

import io.einharjar.domain.Permission;
import io.einharjar.domain.persistence.entity.common.AuditedEntity;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class UserPermissions extends AuditedEntity {
    @Column
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = Permission.class)
    @Setter(value = AccessLevel.NONE)
    private Set<Permission> permissions = new HashSet<>();
}
