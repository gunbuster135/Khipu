package io.einharjar.domain.persistence.entity;

import io.einharjar.domain.persistence.entity.common.AuditedEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class User extends AuditedEntity {
    private String name;
}
