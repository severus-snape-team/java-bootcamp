package com.bootcamp.demo.service;

import com.bootcamp.demo.model.User;
import com.bootcamp.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void saveUser(User user) {
        userRepository.saveUser(user);
    }

    public void deleteRental(User user) {
        userRepository.deleteUser(user);
    }

    public List<User> getUsers() {
        return userRepository.getUsers();
    }

    public User getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

}
