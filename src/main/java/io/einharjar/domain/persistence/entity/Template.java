package io.einharjar.domain.persistence.entity;


import io.einharjar.domain.persistence.entity.common.AuditedEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "Template")
public class Template extends AuditedEntity {
    @Length(max = 128, min = 8)
    @Column(nullable = false)
    private String text;

    private String lang;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_id", nullable = false)
    private Document document;
}
