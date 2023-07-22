package com.example.simplehr02.data.employee.repository;

import com.example.simplehr02.data.employee.entity.EmployeeEntity;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepo extends CrudRepository<EmployeeEntity, Long> {
    EmployeeEntity findByFirstNameAndLastName(String firstName, String lastName);
    EmployeeEntity findByUserId(Long userId);
}
