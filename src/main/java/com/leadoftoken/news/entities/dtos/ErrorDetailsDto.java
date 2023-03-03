package com.leadoftoken.news.entities.dtos;

import java.util.Date;

public class ErrorDetailsDto {
    private Date timestamp;
    private String message;
    private String details;

    public ErrorDetailsDto(Date timestamp, String message, String details) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }
}