package io.einharjar.domain.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.einharjar.domain.TemplateType;
import io.einharjar.domain.persistence.entity.Template;
import lombok.Data;
import lombok.NonNull;
import org.hibernate.validator.constraints.Length;

@Data
public class TemplateShallow {
    @Length(max = 128, min = 8)
    private String text;
    private String lang;
    private TemplateType templateType;

    @JsonProperty(value = "created_at", access = JsonProperty.Access.WRITE_ONLY)
    private Long createdDate;
    @JsonProperty(value = "modified_at", access = JsonProperty.Access.WRITE_ONLY)
    private Long modifiedDate;

    public static TemplateShallow from(@NonNull Template template){
        TemplateShallow templateShallow = new TemplateShallow();
        templateShallow.setText(template.getText());
        templateShallow.setLang(template.getLang());
        templateShallow.setCreatedDate(template.getCreatedDate());
        templateShallow.setModifiedDate(template.getModifiedDate());
        templateShallow.setTemplateType(template.getTemplateType());
        return templateShallow;
    }
}
