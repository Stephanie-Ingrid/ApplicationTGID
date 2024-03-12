package com.teste.TGID.exception;

public class InternalServerErrorException extends RuntimeException {
    public InternalServerErrorException(String message){
        super (message);
    }
}
