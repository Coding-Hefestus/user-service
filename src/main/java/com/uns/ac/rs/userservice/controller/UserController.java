package com.uns.ac.rs.userservice.controller;

import com.uns.ac.rs.userservice.authorization.Authorizable;
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
    public ResponseEntity<User> findById(@PathVariable("id") Integer id){
        return ResponseEntity.ok(userService.findByUserId(id));
    }

    @Authorizable(roles = {"USER", "ADMIN"})
    @PostMapping("/user")
    public ResponseEntity<User> editUser(@RequestBody User user){
        return ResponseEntity.ok(userService.editUser(user));
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user){
        return ResponseEntity.ok(userService.createUser(user));
    }

    @GetMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){
       // System.out.println(serverHttpRequest.getId()); //(ServerHttpRequest request
        return ResponseEntity.ok(userService.login(loginRequest));
    }

    @GetMapping("/test")
    public ResponseEntity<?> test(ServerHttpRequest request){
        System.out.println(request.getId()); //(ServerHttpRequest request
        List<String> authorization = request.getHeaders().get("Authorization");
        System.out.println(authorization);
        return ResponseEntity.ok().build();
    }
}
