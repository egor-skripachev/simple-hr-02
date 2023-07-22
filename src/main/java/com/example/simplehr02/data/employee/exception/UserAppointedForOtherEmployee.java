package com.example.simplehr02.data.employee.exception;

public class UserAppointedForOtherEmployee extends Exception{
    public UserAppointedForOtherEmployee(Long userId) {
        super(String.format("Пользователь %d назначен другому сотруднику", userId));
    }
}
