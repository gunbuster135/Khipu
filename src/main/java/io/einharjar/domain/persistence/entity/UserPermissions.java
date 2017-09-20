package io.einharjar.domain.persistence.entity;

import io.einharjar.domain.Permission;
import io.einharjar.domain.persistence.entity.common.AuditedEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class UserPermissions extends AuditedEntity {
    @Column
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = Permission.class)
    private List<Permission> permissions;
}
