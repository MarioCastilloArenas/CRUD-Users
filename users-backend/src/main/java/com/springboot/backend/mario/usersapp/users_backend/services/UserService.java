package com.springboot.backend.mario.usersapp.users_backend.services;

import java.util.List;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.springboot.backend.mario.usersapp.users_backend.entities.User;


public interface UserService {

    List<User> findAll();

    Page<User> findAll(Pageable pageable);
 
    Optional<User> findById(long id);

    User save(User user);

    void deleteById(Long id);

}
