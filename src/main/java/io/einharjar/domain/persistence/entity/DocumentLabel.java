package io.einharjar.domain.persistence.entity;

import io.einharjar.domain.persistence.entity.common.AuditedEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(
        name="DocumentLabel",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "document_id_label_id_constraint",
                        columnNames = {"document_id, label_id"}  //Do not allow duplicate labels for a document
                )
        }
)
@Data
public class DocumentLabel extends AuditedEntity{
    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name="document_id")
    @NotNull
    private Document document;

    @OneToOne
    @JoinColumn(name="label_id")
    @NotNull
    private Label label;
}
