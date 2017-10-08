package io.einharjar.domain.model.dto;


import io.einharjar.domain.Permission;
import lombok.Data;

import java.util.List;

@Data
public class AccountGroupShallow {
    private Permission permission;
    private List<AccountShallow> accounts;
}
