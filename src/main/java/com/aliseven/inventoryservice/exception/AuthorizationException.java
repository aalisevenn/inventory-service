package com.aliseven.inventoryservice.exception;

import java.util.List;

public class AuthorizationException extends BaseException{
    public AuthorizationException(String message, String code, List<String> details) {
        super(ExceptionType.AUTHORIZATION, message, code, details);
    }
    public AuthorizationException(String message, String code) {
        super(ExceptionType.AUTHORIZATION, message, code);
    }

    public AuthorizationException(String code) {
        super(ExceptionType.AUTHORIZATION, code);
    }
}
