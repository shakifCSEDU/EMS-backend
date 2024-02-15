package com.example.todo.exception;

import org.springframework.http.HttpStatus;

public class TodoAPIException extends RuntimeException{
    private HttpStatus status;
    private String message;

    public TodoAPIException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public TodoAPIException(String message, HttpStatus status, String message1) {
        super(message);
        this.status = status;
        this.message = message1;
    }

    public TodoAPIException(String message, Throwable cause, HttpStatus status, String message1) {
        super(message, cause);
        this.status = status;
        this.message = message1;
    }

    public TodoAPIException(Throwable cause, HttpStatus status, String message) {
        super(cause);
        this.status = status;
        this.message = message;
    }

    public TodoAPIException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, HttpStatus status, String message1) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.status = status;
        this.message = message1;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
