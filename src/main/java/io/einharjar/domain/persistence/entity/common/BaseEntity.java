package io.einharjar.domain.persistence.entity.common;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;


@MappedSuperclass
@Data
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Setter(value = AccessLevel.NONE)
    private Long id;
}
