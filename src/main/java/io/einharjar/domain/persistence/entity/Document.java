package io.einharjar.domain.persistence.entity;

import io.einharjar.domain.TemplateEngine;
import io.einharjar.domain.persistence.entity.common.AuditedEntity;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Table(
        name = "Document",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "document_id_name_constraint",
                        columnNames = {"domain_id, name"}  //Documents belonging to same domain should have unique names
                )
        }
)
@Entity
public class Document extends AuditedEntity {
    @Length(max = 128, min = 8)
    @Column(nullable = false, name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "domain_id", nullable = false)
    private Domain domain;

    @OneToMany(mappedBy = "document", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @Setter(value = AccessLevel.NONE)
    private List<Template> templates = new ArrayList<>();

    @ElementCollection(targetClass = String.class)
    @CollectionTable(name = "document_properties")
    @MapKeyColumn(name = "key")
    @Column(name = "value")
    @Setter(value = AccessLevel.NONE)
    private Map<String, String> properties = new HashMap<>();

    @Enumerated(EnumType.STRING)
    private TemplateEngine templateEngine;

    private Set<DocumentLabel> labels;

    public void addProperty(String key, String value) {
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(value))
            throw new IllegalArgumentException("Key or/and value cannot be nulL!");
        this.properties.put(key, value);
    }

    public void addProperties(@NonNull Map<String, String> properties) {
        this.properties.putAll(properties);
    }

    public void addTemplate(@NonNull Template template) {
        this.templates.add(template);
    }

    public void addTemplate(@NonNull List<Template> templates) {
        this.templates.addAll(templates);
    }

    public void addDocumentLabel(@NonNull List<DocumentLabel> documentLabels) {
        this.labels.addAll(documentLabels);
    }

    public void addDocumentLabel(@NonNull DocumentLabel documentLabel) {
        this.labels.add(documentLabel);
    }
}
