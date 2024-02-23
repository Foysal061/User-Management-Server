package com.acterio.assessment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    public boolean isValidDomain(String email) {
        if (email.endsWith("@hotmail.com") || email.endsWith("@gmail.com") || email.endsWith("@outlook.com")) {
            return true;
        }
        return false;
    }

    public boolean isUserExistsByEmail(String email) {
        UserEntity user = userRepository.findByEmail(email);
        if (user == null) {
            return false;
        }
        return true;
    }

    public void createUser(String email, String password) {
        UserEntity user = new UserEntity();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    public boolean isValidLoginCredentials(String email, String password) {
        UserEntity user = userRepository.findByEmail(email);
        if (user == null || !passwordEncoder.matches(password, user.getPassword())) {
            return false;
        }
        return true;
    }

    public List<DomainCountDTO> getDomainCount() {
        return userRepository.countDistinctEmailDomain();
    }
}
