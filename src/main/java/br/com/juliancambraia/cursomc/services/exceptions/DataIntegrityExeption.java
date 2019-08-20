package br.com.juliancambraia.cursomc.services.exceptions;

import org.springframework.dao.DataIntegrityViolationException;

public class DataIntegrityExeption extends DataIntegrityViolationException {

    private static final long serialVersionUID = 1L;

    public DataIntegrityExeption(String msg) {
        super(msg);
    }

    public DataIntegrityExeption(String msg, Throwable cause) {
        super(msg, cause);
    }

}
