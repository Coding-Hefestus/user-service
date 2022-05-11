package com.uns.ac.rs.userservice.controller;

import com.uns.ac.rs.userservice.model.User;
import com.uns.ac.rs.userservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("/api/user-service")
public class UserController {

    private final UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<User>> findAllUsers(){
        return ResponseEntity.ok(userService.fetchAllUsers());
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> findById(@PathVariable("id") Integer id){
        return ResponseEntity.ok(userService.findByUserId(id));
    }

    @PostMapping("/user")
    public ResponseEntity<User> editUser(@RequestBody User user){
        return ResponseEntity.ok(userService.editUser(user));
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user){
        return ResponseEntity.ok(userService.createUser(user));
    }
}
