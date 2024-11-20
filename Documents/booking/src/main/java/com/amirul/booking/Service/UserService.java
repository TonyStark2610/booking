package com.amirul.booking.Service;

import com.amirul.booking.Entity.User;
import com.amirul.booking.Dto.UserDto;
import com.amirul.booking.Repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public void registerUser(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setCredits(100);
        userRepository.save(user);
    }

    public boolean validateUser(String email, String password) {
        Optional<User> userOptional = Optional.ofNullable(userRepository.findByEmail(email));
        if (userOptional.isPresent()) {
            User user = userOptional.get();

            boolean matches = passwordEncoder.matches(password, user.getPassword());
            System.out.println("Password matches: " + matches);
            return matches;
        }
        System.out.println("User not found for email: " + email);
        return false;
    }


    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }
}

