package com.leadoftoken.news.exceptions;

import org.springframework.http.HttpStatus;

public class NewsApiException extends RuntimeException{
    private HttpStatus status;
    private String message;

    public NewsApiException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public NewsApiException(String message, HttpStatus status, String message1) {
        super(message);
        this.status = status;
        this.message = message1;
    }


    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
