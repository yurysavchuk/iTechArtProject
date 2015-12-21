package com.yurysavchuk.dao.mysql;

public class DaoException extends Exception {
    private Exception exception;

    public DaoException(String err) {
        super(err);
    }

    public DaoException(String err, Exception e) {
        super(err);
        this.exception = e;
    }
}
