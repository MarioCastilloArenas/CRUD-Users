package com.springboot.backend.mario.usersapp.users_backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.backend.mario.usersapp.users_backend.entities.User;
import com.springboot.backend.mario.usersapp.users_backend.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UserRepository uRepository;
    
    @Override
    @Transactional(readOnly = false)
    public List<User> findAll() {
        return (List) this.uRepository.findAll();
    }
    
    @Override
    @Transactional(readOnly = false)
    public Page<User> findAll(Pageable pageable) {
        return this.uRepository.findAll(pageable);
    }
    
    @Override
    @Transactional(readOnly = false)
    public Optional<User> findById(long id) {
        return this.uRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = false)
    public User save(User user) {
        return this.uRepository.save(user);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteById(Long id) {
        uRepository.deleteById(id);
    }


}
