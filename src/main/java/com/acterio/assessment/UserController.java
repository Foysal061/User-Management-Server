package com.acterio.assessment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/register-user")
    public ResponseEntity<String> registerUser(@RequestBody UserRegistrationDTO user) {
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            return ResponseEntity.badRequest().body("Email cannot be null");
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            return ResponseEntity.badRequest().body("Password cannot be null");
        }
        if (!userService.isValidDomain(user.getEmail())) {
            return ResponseEntity.badRequest()
                    .body("Email domain should be @hotmail.com or @gmail.com or @outlook.com");
        }
        if (userService.isUserExistsByEmail(user.getEmail())) {
            return ResponseEntity.badRequest()
                    .body("User already exists.");
        }
        userService.createUser(user.getEmail(), user.getPassword());
        return ResponseEntity.ok("User creation successful");

    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserRegistrationDTO user) {
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            return ResponseEntity.badRequest().body("Email cannot be null");
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            return ResponseEntity.badRequest().body("Password cannot be null");
        }
        if (!userService.isValidDomain(user.getEmail())) {
            return ResponseEntity.badRequest()
                    .body("Email domain should be @hotmail.com or @gmail.com or @outlook.com");
        }
        if (!userService.isValidLoginCredentials(user.getEmail(),user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email/password");
        }
        return ResponseEntity.ok("Login successful");
    }


}
