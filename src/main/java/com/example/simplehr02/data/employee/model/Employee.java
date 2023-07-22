package com.example.simplehr02.data.employee.model;

import com.example.simplehr02.data.common.enums.Sex;
import com.example.simplehr02.data.employee.entity.EmployeeEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
public class Employee {

    private Long id;
    private String lastName;
    private String firstName;
    private String middleName;
    private Date birthdate;
    private Sex sex;
    private Long userId;

    public static Employee toModel(EmployeeEntity entity) {
        Employee model = new Employee();
        model.setId(entity.getId());
        model.setFirstName(entity.getFirstName());
        model.setLastName(entity.getLastName());
        model.setBirthdate(entity.getBirthdate());
        model.setSex(entity.getSex());
        model.setUserId(entity.getUser().getId());
        return model;
    }
}
