package com.example.simplehr02.data.employee.entity;

import com.example.simplehr02.data.basic.entity.UserEntity;
import com.example.simplehr02.data.common.enums.Gender;
import com.example.simplehr02.data.employee.model.Employee;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class EmployeeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String lastName;
    @NotEmpty
    private String firstName;
    private String middleName;
    private Date birthdate;

    @Enumerated
    @NotNull
    private Gender gender;

    private Long userId;


    public static EmployeeEntity toEntity(Employee model) {
        EmployeeEntity entity = new EmployeeEntity();
        entity.setId(model.getId());
        entity.setFirstName(model.getFirstName());
        entity.setLastName(model.getLastName());
        entity.setMiddleName(model.getMiddleName());
        entity.setBirthdate(model.getBirthdate());
        entity.setGender(model.getGender());
        entity.setUserId(model.getUserId());
        return entity;
    }
}
