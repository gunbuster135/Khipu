package io.einharjar.domain.persistence.entity;

import io.einharjar.domain.persistence.entity.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Label extends BaseEntity {
    @NonNull
    @Length(max = 128, min = 4)
    private String name;
}
