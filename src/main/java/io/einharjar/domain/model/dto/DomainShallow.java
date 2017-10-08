package io.einharjar.domain.model.dto;


import io.einharjar.domain.Permission;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import java.util.List;
import java.util.Map;

@Data
public class DomainShallow {
    @Length(max = 128, min = 4)
    private String name;
    private List<DocumentShallow> documents;
    private Map<Permission, AccountGroupShallow> permissions;
}
