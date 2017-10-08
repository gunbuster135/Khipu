package io.einharjar.domain.persistence.entity;


import io.einharjar.domain.persistence.entity.common.AuditedEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "Token")
public class Token extends AuditedEntity {
    @Column(nullable = false, unique = true)
    private String value;
    private Long validUntil;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;
}
