package com.uns.ac.rs.userservice.controller;

import com.uns.ac.rs.userservice.authorization.Authorizable;
import com.uns.ac.rs.userservice.model.AuthenticationResponse;
import com.uns.ac.rs.userservice.model.LoginRequest;
import com.uns.ac.rs.userservice.model.LoginResponse;
import com.uns.ac.rs.userservice.model.User;
import com.uns.ac.rs.userservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("/api/user-service")
public class UserController {

    private final UserService userService;;

    @Authorizable(roles = {"ADMIN"})
    @GetMapping("/users")
    public ResponseEntity<List<User>> findAllUsers(ServerHttpRequest request){

        return ResponseEntity.ok(userService.fetchAllUsers());
    }

    @Authorizable(roles = {"USER", "ADMIN"})
    @GetMapping("/user/{id}")
    public ResponseEntity<User> findById(ServerHttpRequest request, @PathVariable("id") Integer id){
        return ResponseEntity.ok(userService.findByUserId(id));
    }

    @Authorizable(roles = {"USER", "ADMIN"})
    @PostMapping("/user")
    public ResponseEntity<User> editUser(ServerHttpRequest request, @RequestBody User user){
        return ResponseEntity.ok(userService.editUser(user));
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(ServerHttpRequest request, @RequestBody User user){
        return ResponseEntity.ok(userService.createUser(user));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(ServerHttpRequest request, @RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(userService.login(loginRequest));
    }

    @GetMapping("/user/authenticated/{jwt}")
    public ResponseEntity<AuthenticationResponse> authenticate(@PathVariable("jwt") String jwt){
        return ResponseEntity.ok(userService.authenticate(jwt));
    }


}
