package io.einharjar.domain.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.einharjar.domain.TemplateEngine;
import io.einharjar.domain.persistence.entity.Document;
import io.einharjar.domain.persistence.entity.DocumentLabel;
import io.einharjar.domain.persistence.entity.Template;
import lombok.Data;
import lombok.NonNull;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.*;

@Data
public class DocumentShallow {
    @Length(max = 128, min = 8)
    @JsonProperty("name")
    private String name;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<TemplateShallow> templateShallows = new ArrayList<>();
    @JsonProperty("properties")
    private Map<String,String> properties = new HashMap<>();
    @NotNull
    private TemplateEngine templateEngine;
    private List<LabelShallow> labels = new ArrayList<>();
    @JsonProperty(value = "created_at", access = JsonProperty.Access.WRITE_ONLY)
    private Long createdDate;
    @JsonProperty(value = "modified_at", access = JsonProperty.Access.WRITE_ONLY)
    private Long modifiedDate;


    public static DocumentShallow from(@NonNull Document document){
        DocumentShallow documentShallow = new DocumentShallow();
        documentShallow.setName(document.getName());
        documentShallow.setTemplateEngine(document.getTemplateEngine());
        documentShallow.setProperties(document.getProperties());
        for(DocumentLabel label : document.getLabels()){
           documentShallow.getLabels().add(LabelShallow.from(label.getLabel()));
        }
        for(Template template : document.getTemplates()){
            documentShallow.getTemplateShallows().add(TemplateShallow.from(template));
        }
        documentShallow.setCreatedDate(document.getCreatedDate());
        documentShallow.setModifiedDate(document.getModifiedDate());
        return documentShallow;
    }
}
