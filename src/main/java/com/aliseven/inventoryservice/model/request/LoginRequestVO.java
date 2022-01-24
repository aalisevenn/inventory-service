package com.aliseven.inventoryservice.model.request;

import lombok.Data;

@Data
public class LoginRequestVO {
    private String username;
    private String password;
}
