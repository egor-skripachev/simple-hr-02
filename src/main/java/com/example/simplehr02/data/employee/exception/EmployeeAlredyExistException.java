package com.example.simplehr02.data.employee.exception;

public class EmployeeAlredyExistException extends Exception{
    public EmployeeAlredyExistException(String firstName, String lastName) {
        super(String.format("Пользователь %s %s уже существует", firstName, lastName));
    }
}
