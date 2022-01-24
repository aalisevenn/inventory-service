package com.aliseven.inventoryservice.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequestVO {
    private String username;
    private String password;
    private String email;
    private String userType;
    private Set<String> roles;
}
