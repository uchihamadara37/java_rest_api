package com.andre.dojo.dataModel;

public class Metadata <T> {
    private int status;
    private String message;
    private T data;

    public Metadata(String message, int status, T data){
        this.message = message;
        this.status = status;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}
