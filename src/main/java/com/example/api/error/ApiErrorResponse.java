package com.example.api.error;

import java.util.List;

/**
 * @author magiccrafter
 */
public class ApiErrorResponse {

    private int status;
    private int code;
    private String message;
    private List<String> errors;

    public ApiErrorResponse(int status, int code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public ApiErrorResponse(int status, int code, String message, List<String> errors) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.errors = errors;
    }

    public int getStatus() {
        return this.status;
    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public List<String> getErrors() {
        return this.errors;
    }

    @Override
    public String toString() {
        return "ApiErrorResponse{" +
                "status=" + this.status +
                ", code=" + this.code +
                ", message=" + this.message +
                '}';
    }
}
