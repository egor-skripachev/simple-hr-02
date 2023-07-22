package com.example.simplehr02.data.employee.service;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestPart;

import java.util.List;
import java.util.Set;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepo employeeRepo;
    @Autowired
    private Validator validator;

    public EmployeeEntity create(Employee employee) throws EmployeeAlredyExistException,
            UserAppointedForOtherEmployee, ConstraintViolationException {

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
        if (employee.getUserId() != null && employeeRepo.findByUserId(employee.getUserId()) != null) {
            throw new UserAppointedForOtherEmployee(employee.getUserId());
        }
        return employeeRepo.save(EmployeeEntity.toEntity(employee));
    }

    @GetMapping
    public List<EmployeeEntity> getAll() {
        return (List<EmployeeEntity>) employeeRepo.findAll();
    }

    @GetMapping("{id}")
    public EmployeeEntity get(@RequestPart Long id) throws EmployeeNotFoundException {
        if (employeeRepo.findById(id) != null) {
            throw new EmployeeNotFoundException("Сотрудник не найден");
        }
        return employeeRepo.findById(id).get();
    }

}
