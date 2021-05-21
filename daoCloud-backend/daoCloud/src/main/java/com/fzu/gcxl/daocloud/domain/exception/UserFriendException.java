package com.fzu.gcxl.daocloud.domain.exception;

public class UserFriendException extends RuntimeException {
    public UserFriendException(String msg) {
        super(msg);
    }

    public UserFriendException() {
        super();
    }
}
