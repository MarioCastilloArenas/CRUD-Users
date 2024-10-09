package com.springboot.backend.mario.usersapp.users_backend.controllers;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.HashMap;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.backend.mario.usersapp.users_backend.entities.User;
import com.springboot.backend.mario.usersapp.users_backend.services.UserService;

import jakarta.validation.Valid;


@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService uService;

    @GetMapping
    public List<User>  list(){
        return  uService.findAll();
    }

    
    @GetMapping("/page/{page}")
    public Page<User>  listPageable(@PathVariable Integer page){
        Pageable pageable = PageRequest.of(page, 4);
        return  uService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable Long id) {
        Optional<User> userOptional = uService.findById(id);
        if(userOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(userOptional.orElseThrow());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("error", "el usuario no se encontró por el id:" + id));
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody User user, BindingResult result){

        if (result.hasErrors()) {
            return validation(result);
        }
        
        return ResponseEntity.status(HttpStatus.CREATED).body(uService.save(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody User user, BindingResult result ,@PathVariable Long id){

        if (result.hasErrors()) {
            return validation(result);
        }

        Optional<User> userOptional = uService.findById(id);
        if (userOptional.isPresent()) {

            User userBBDD = userOptional.get();
            
            userBBDD.setName(user.getName());
            userBBDD.setLastname(user.getLastname());
            userBBDD.setEmail(user.getEmail());
            userBBDD.setUsername(user.getUsername());
            userBBDD.setPassword(user.getPassword());
            return ResponseEntity.ok(uService.save(userBBDD));

        }

        return ResponseEntity.notFound().build();
    
    }

    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(error -> {
            errors.put(error.getField(), "El campo " +error.getField() + " " + error.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id ){
        Optional<User> userOptional = uService.findById(id);
        if (userOptional.isPresent()) {
            uService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
