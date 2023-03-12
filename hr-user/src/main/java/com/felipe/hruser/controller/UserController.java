package com.felipe.hruser.controller;

import com.felipe.hruser.entity.User;
import com.felipe.hruser.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(value = "/users")
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id) {
        Optional<User> foundUser = userRepository.findById(id);
        User user = foundUser.orElseThrow(() -> new RuntimeException("User not found"));
        return ResponseEntity.ok(user);
    }

    @GetMapping()
    public ResponseEntity<User> findByEmail(@RequestParam String email) {
        User foundUser = userRepository.findByEmail(email);
        if (foundUser == null) {
            throw new RuntimeException("User not found");
        }
        return ResponseEntity.ok(foundUser);
    }
}
