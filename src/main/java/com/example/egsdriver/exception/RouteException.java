package com.example.egsdriver.exception;

public class RouteException extends RuntimeException{
    private String msg;

    public RouteException(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
