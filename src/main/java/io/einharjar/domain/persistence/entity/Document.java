package io.einharjar.domain.persistence.entity;

import io.einharjar.domain.TemplateEngine;
import io.einharjar.domain.persistence.entity.common.AuditedEntity;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "Document")
@Entity
public class Document extends AuditedEntity{
    @Length(max = 128 , min = 8)
    @Column(unique = true, nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "domain_id", nullable = false)
    private Domain domain;

    @OneToMany(mappedBy="document", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval=true)
    @Setter(value = AccessLevel.NONE)
    private List<Template> templates = new ArrayList<>();

    @ElementCollection(targetClass = String.class)
    @CollectionTable(name = "document_properties")
    @MapKeyColumn(name="key")
    @Column(name="value")
    @Setter(value = AccessLevel.NONE)
    private Map<String,String> properties = new HashMap<>();

    @Enumerated(EnumType.STRING)
    private TemplateEngine templateEngine;
}
