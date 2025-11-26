package com.projetos.validadorboletos.exception;

public class BoletoInvalidoException extends RuntimeException {
    public BoletoInvalidoException(String msg) {
        super(msg);
    }
}