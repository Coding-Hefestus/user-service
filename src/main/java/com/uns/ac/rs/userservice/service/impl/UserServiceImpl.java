package com.uns.ac.rs.userservice.service.impl;

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

    @Override
    public List<User> fetchAllUsers() {
        return userRepository.findByRole("USER");
    }

    @Override
    public User findByUserId(int userId) {
        return userRepository.getById(userId);
    }

    @Override
    public User createUser(User user) {
        return null;
    }

    @Override
    public User editUser(User user) {
        return null;
    }
}
