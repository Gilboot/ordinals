package com.github.ordinals;

public final class OrdinalsException extends RuntimeException {
    public OrdinalsException(String msg) {
        super(msg);
    }

    public OrdinalsException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
