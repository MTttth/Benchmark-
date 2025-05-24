package com.example.commom.exception;



public class AccountAlreadyExit extends BaseException {
    public AccountAlreadyExit() {}
    public AccountAlreadyExit(String message) {
        super(message);
    }
}
