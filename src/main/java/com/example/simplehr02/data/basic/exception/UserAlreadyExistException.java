package com.example.simplehr02.data.basic.exception;

public class UserAlreadyExistException extends Exception{
    public UserAlreadyExistException(String username) {
        super(String.format("Пользователь %s уже существует", username));
    }


}
