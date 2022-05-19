package com.uns.ac.rs.userservice.service;

import com.uns.ac.rs.userservice.model.AuthenticationResponse;
import com.uns.ac.rs.userservice.model.LoginRequest;
import com.uns.ac.rs.userservice.model.LoginResponse;
import com.uns.ac.rs.userservice.model.User;

import java.util.List;

public interface UserService {

    List<User> fetchAllUsers();

    User findByUserId(int userId);

    User createUser(User user);

    User editUser(User user);

    LoginResponse login(LoginRequest loginRequest);

    AuthenticationResponse authenticate(String jwt);

}
