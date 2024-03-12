package com.teste.TGID.exception;

public class TransacaoNaoAutorizadaException extends  RuntimeException {
    public TransacaoNaoAutorizadaException(String message){
        super (message);
    }
}

