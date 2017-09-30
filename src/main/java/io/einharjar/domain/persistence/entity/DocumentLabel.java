package io.einharjar.domain.persistence.entity;

import io.einharjar.domain.persistence.entity.common.AuditedEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name="DocumentLabel")
@Data
public class DocumentLabel extends AuditedEntity{
    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name="document_id")
    @NotNull
    private Document document;

    @OneToOne
    @JoinColumn(name="tag_id")
    @NotNull
    private Label label;
}
