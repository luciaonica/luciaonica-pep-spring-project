package com.example.exception;

public class InvalidMessageFormatException extends Exception{
    public InvalidMessageFormatException(String message){
        super(message);
    }
}
