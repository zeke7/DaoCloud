package com.fzu.gcxl.daocloud.domain.exception;

public class CustomUnauthorizedException extends RuntimeException {
    public CustomUnauthorizedException(String msg) {
        super(msg);
    }

    public CustomUnauthorizedException() {
        super();
    }
}
