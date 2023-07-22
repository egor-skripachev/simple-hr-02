package com.example.simplehr02.data.employee.entity;

import com.example.simplehr02.data.basic.entity.UserEntity;
import com.example.simplehr02.data.common.enums.Sex;
import com.example.simplehr02.data.employee.model.Employee;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank
    private String lastName;
    @NotBlank
    private String firstName;
    private String middleName;
    private Date birthdate;
    @Enumerated
    private Sex sex;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;


    public static EmployeeEntity toEntity(Employee model) {
        EmployeeEntity entity = new EmployeeEntity();
        entity.setId(model.getId());
        entity.setFirstName(model.getFirstName());
        entity.setLastName(model.getLastName());
        entity.setBirthdate(model.getBirthdate());
        entity.setSex(model.getSex());
        UserEntity user = new UserEntity();
        user.setId(model.getUserId());
        entity.setUser(user);
        return entity;
    }
}
