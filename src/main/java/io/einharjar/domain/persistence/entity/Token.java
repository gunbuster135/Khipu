package io.einharjar.domain.persistence.entity;


import io.einharjar.domain.persistence.entity.common.AuditedEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Token extends AuditedEntity {
    @Column(nullable = false)
    private String value;
    private Long validUntil;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
