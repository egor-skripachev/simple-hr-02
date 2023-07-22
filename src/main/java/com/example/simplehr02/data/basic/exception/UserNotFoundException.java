package com.example.simplehr02.data.basic.exception;

public class UserNotFoundException extends Exception{
    public UserNotFoundException(Long id) {
        super(String.format("Пользователь с id %d не существует", id));
    }
}
