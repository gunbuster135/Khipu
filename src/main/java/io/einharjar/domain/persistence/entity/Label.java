package io.einharjar.domain.persistence.entity;

import io.einharjar.domain.persistence.entity.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Label extends BaseEntity {
    @NonNull
    private String name;
}
