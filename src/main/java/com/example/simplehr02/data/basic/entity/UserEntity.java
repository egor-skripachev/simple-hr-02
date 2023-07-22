package com.example.simplehr02.data.basic.entity;

import com.example.simplehr02.data.basic.model.User;
import com.example.simplehr02.data.employee.entity.EmployeeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String username;
    @NotNull
    private String password;

    @OneToOne(mappedBy = "user")
    private EmployeeEntity employee;

}
