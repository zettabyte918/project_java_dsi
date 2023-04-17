package com.projet.utils;

public class TestDBResponse {
    private int status;
    private String message;

    public TestDBResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
