package com.springMVC.demo1.exception;

public class MyException extends Exception {
    private String message;
    public MyException(){
        super();
    }
    public MyException(String message){
        super();
        this.message=message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
