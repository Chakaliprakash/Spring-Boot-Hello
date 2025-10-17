package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AdminUserController {

    private final UserRepository userRepository;

    public AdminUserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/admin/users")
    public List<UserDTO> listUsers() {
        return userRepository.findAll()
                .stream()
                .map(u -> new UserDTO(u.getId(), u.getUsername(), u.getPassword()))
                .collect(Collectors.toList());
    }

    public record UserDTO(Long id, String username, String passwordHash) {}
}
