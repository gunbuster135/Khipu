package io.einharjar.domain.model.dto;

import io.einharjar.domain.persistence.entity.Label;
import lombok.Data;
import lombok.NonNull;
import org.hibernate.validator.constraints.Length;

@Data
public class LabelShallow {
    @Length(max = 128, min = 4)
    private String name;

    public static LabelShallow from(@NonNull Label label) {
        LabelShallow labelShallow = new LabelShallow();
        labelShallow.setName(label.getName());
        return labelShallow;
    }
}
