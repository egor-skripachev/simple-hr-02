package com.example.simplehr02.data.basic.repository;

import com.example.simplehr02.data.basic.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends CrudRepository<UserEntity, Long> {
    UserEntity findByUsername(String username);
}
