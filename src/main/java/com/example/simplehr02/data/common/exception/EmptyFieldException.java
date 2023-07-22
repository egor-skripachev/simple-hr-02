package com.example.simplehr02.data.common.exception;

public class EmptyFieldException extends Exception{
    public EmptyFieldException(String field) {
        super(String.format("Поле %s не заполнено", field));
    }
}
