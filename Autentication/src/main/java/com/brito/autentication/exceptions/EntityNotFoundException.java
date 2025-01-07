package com.brito.autentication.exceptions;

import lombok.Getter;

@Getter
public class EntityNotFoundException extends RuntimeException {

    private String recurso;
    private String codigo;

    public EntityNotFoundException(String str) {
        super(str);
    }

    public EntityNotFoundException(String codigo, String recurso) {
        this.recurso = recurso;
        this.codigo = codigo;
    }
}
