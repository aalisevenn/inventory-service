package com.aliseven.inventoryservice.exception;

import lombok.Data;

import java.util.List;

@Data
public class BaseException extends RuntimeException {

    private ExceptionType type;
    private String message;
    private String code;
    private List<String> details;

    public BaseException(ExceptionType type, String message, String code, List<String> details) {
        this(type, message, code);
        this.details = details;
    }

    public BaseException(ExceptionType type, String message, String code) {
        this(type, code);
        this.message = message;
    }

    public BaseException(ExceptionType type, String code) {
        this.type = type;
        this.code = code;
    }
}
