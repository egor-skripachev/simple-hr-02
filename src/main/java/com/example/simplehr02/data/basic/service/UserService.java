package com.example.simplehr02.data.basic.service;

import com.example.simplehr02.data.basic.entity.UserEntity;
import com.example.simplehr02.data.common.exception.EmptyFieldException;
import com.example.simplehr02.data.basic.exception.UserAlreadyExistException;
import com.example.simplehr02.data.basic.exception.UserNotFoundException;
import com.example.simplehr02.data.basic.model.User;
import com.example.simplehr02.data.basic.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    public List<User> getAll() {
        List<UserEntity> entity = (List<UserEntity>) userRepo.findAll();
        return entity.stream().map(user -> User.toModel(user)).collect(Collectors.toList());
    }

    public User getById(Long id) throws UserNotFoundException{
        if (userRepo.findById(id).orElse(null) == null) {
            throw new UserNotFoundException(id);
        }
        return User.toModel(userRepo.findById(id).get());
    }

    public User create(UserEntity user) throws UserAlreadyExistException, EmptyFieldException {
        if (user.getUsername().isEmpty())
            throw new EmptyFieldException("username");
        if (user.getPassword().isEmpty())
            throw new EmptyFieldException("password");

        if (userRepo.findByUsername(user.getUsername()) != null)
            throw new UserAlreadyExistException(user.getUsername());

        return User.toModel(userRepo.save(user));
    }

    public User update(UserEntity user) throws UserNotFoundException,
            UserAlreadyExistException, EmptyFieldException{

        if (user.getUsername().isEmpty())
            throw new EmptyFieldException("username");

        UserEntity userById = userRepo.findById(user.getId()).get();
        if (userById == null)
            throw new UserNotFoundException(user.getId());

        if (user.getPassword() == null)
            user.setPassword(userById.getPassword());

        UserEntity userByUsername = userRepo.findByUsername(user.getUsername());
        if (userByUsername != null && userByUsername.getId() != user.getId())
            throw new UserAlreadyExistException(user.getUsername());

        return User.toModel(userRepo.save(user));
    }

    public Long delete(Long id) throws UserNotFoundException{
        if (userRepo.findById(id).orElse(null) == null) {
            throw new UserNotFoundException(id);
        }
        userRepo.deleteById(id);
        return id;
    }
}
