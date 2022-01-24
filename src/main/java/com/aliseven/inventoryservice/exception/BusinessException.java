package com.aliseven.inventoryservice.exception;


import java.util.List;

public class BusinessException extends BaseException {

    public BusinessException(String message, String code, List<String> details) {
        super(ExceptionType.BUSINESS, message, code, details);
    }

    public BusinessException(String message, String code) {
        super(ExceptionType.BUSINESS, message, code);
    }

    public BusinessException(String code) {
        super(ExceptionType.BUSINESS, code);
    }
}
