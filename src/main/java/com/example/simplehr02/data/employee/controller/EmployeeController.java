package com.example.simplehr02.data.employee.controller;

import com.example.simplehr02.data.employee.entity.EmployeeEntity;
import com.example.simplehr02.data.employee.model.Employee;
import com.example.simplehr02.data.employee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/employees/")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    public ResponseEntity create(@RequestBody Employee employee) {
        try {
            return ResponseEntity.ok(employeeService.create(employee));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity getAll() {
        try {
            return ResponseEntity.ok(employeeService.getAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
