package com.example.simplehr02.data.employee.service;

import com.example.simplehr02.data.basic.exception.UserNotFoundException;
import com.example.simplehr02.data.basic.repository.UserRepo;
import com.example.simplehr02.data.employee.entity.EmployeeEntity;
import com.example.simplehr02.data.employee.exception.EmployeeAlredyExistException;
import com.example.simplehr02.data.employee.exception.EmployeeNotFoundException;
import com.example.simplehr02.data.employee.exception.UserAppointedForOtherEmployee;
import com.example.simplehr02.data.employee.model.Employee;
import com.example.simplehr02.data.employee.repository.EmployeeRepo;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepo employeeRepo;
    @Autowired
    private Validator validator;

    @Autowired
    private UserRepo userRepo;

    public EmployeeEntity create(Employee employee) throws EmployeeAlredyExistException,
            UserAppointedForOtherEmployee, ConstraintViolationException,
            UserNotFoundException {

        Set<ConstraintViolation<Employee>> violations = validator.validate(employee);
        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<Employee> constraintViolation : violations) {
                sb.append(constraintViolation.getMessage());
            }
            throw new ConstraintViolationException("Error occurred: " + sb.toString(), violations);
        }

        if (employeeRepo.findByFirstNameAndLastName(employee.getFirstName(), employee.getLastName()) != null) {
            throw new EmployeeAlredyExistException(employee.getFirstName(), employee.getLastName());
        }
        if (employee.getUserId() != null && userRepo.findById(employee.getUserId()).orElse(null) == null) {
            throw new UserNotFoundException(employee.getUserId());
        }
        if (employee.getUserId() != null && employeeRepo.findByUserId(employee.getUserId()) != null) {
            throw new UserAppointedForOtherEmployee(employee.getUserId());
        }
        return employeeRepo.save(EmployeeEntity.toEntity(employee));
    }

    public List<Employee> getAll() {
        List<EmployeeEntity> entity = (List<EmployeeEntity>) employeeRepo.findAll();
        return entity.stream().map(employee -> Employee.toModel(employee)).collect(Collectors.toList());
    }

    public Employee getById(Long id) throws EmployeeNotFoundException {
        employeeRepo.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
        return Employee.toModel(employeeRepo.findById(id).get());
    }

    public Employee update(Employee employee) throws EmployeeNotFoundException ,
            EmployeeAlredyExistException, UserNotFoundException ,
            UserAppointedForOtherEmployee {

        if (employeeRepo.findByFirstNameAndLastName(employee.getFirstName(), employee.getLastName()) != null) {
            throw new EmployeeAlredyExistException(employee.getFirstName(), employee.getLastName());
        }
        if (employee.getUserId() != null && userRepo.findById(employee.getUserId()).orElse(null) == null) {
            throw new UserNotFoundException(employee.getUserId());
        }
        if (employee.getUserId() != null && employeeRepo.findByUserId(employee.getUserId()) != null) {
            throw new UserAppointedForOtherEmployee(employee.getUserId());
        }
        return Employee.toModel(employeeRepo.save(EmployeeEntity.toEntity(employee)));
    }

}
