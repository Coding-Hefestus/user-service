package com.uns.ac.rs.userservice.service.impl;

import com.uns.ac.rs.userservice.jwt.JwtGenerator;
import com.uns.ac.rs.userservice.model.LoginRequest;
import com.uns.ac.rs.userservice.model.LoginResponse;
import com.uns.ac.rs.userservice.model.User;
import com.uns.ac.rs.userservice.repository.UserRepository;
import com.uns.ac.rs.userservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtGenerator jwtGenerator;

    @Override
    public List<User> fetchAllUsers() {
        return userRepository.findByRole("USER");
    }

    @Override
    public User findByUserId(int userId) {
        User byId = userRepository.findById(userId).get(); //test
        return byId;
    }

    @Override
    public User createUser(User user) {
        user.setRole("USER");
        return userRepository.save(user);
    }

    @Override
    public User editUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        User u = userRepository.findByUsernameAndPassword(loginRequest.getUsername(), loginRequest.getPassword());
        if (u == null) return LoginResponse.builder().build();
        return LoginResponse.builder().jwt(jwtGenerator.generate(u)).build();
    }
}
