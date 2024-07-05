package com.andre.dojo.helper;

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
        if (data == null){
            return null;
        }else{
            return data;
        }
    }
}
