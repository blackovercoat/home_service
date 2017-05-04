package com.dolphine.my_services.dto;

/**
 * Created by PC on 4/3/2017.
 */
public class CustomException extends Exception {
    private static final long serialVersionUID = 1L;
    private String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }
    public CustomException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }
    public CustomException() {
        super();
    }
}
