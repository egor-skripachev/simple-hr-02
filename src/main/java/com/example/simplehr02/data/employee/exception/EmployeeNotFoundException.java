package com.example.simplehr02.data.employee.exception;

public class EmployeeNotFoundException extends Exception{
    public EmployeeNotFoundException(Long id) {
        super(String.format("Сотрудник с id %d не существует", id));
    }
}
