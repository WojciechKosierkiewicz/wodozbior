package com.example.wodozbior.service;

import com.example.wodozbior.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordService passwordService;

    public User getUserById(String id) {
        return userRepository.findById(id).orElse(null);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User registerUser(User user) {
        return userRepository.save(user);
    }

    public boolean emailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public void changePassword(String userId, String oldPassword, String newPassword) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (passwordService.matches(oldPassword, user.getPassword())) {
            user.setPassword(passwordService.encodePassword(newPassword));
            userRepository.save(user);
        } else {
            throw new IllegalArgumentException("Old password is incorrect");
        }
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

    // Metoda weryfikująca dane logowania
    public boolean isValidUser(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email); // Sprawdzamy, czy użytkownik istnieje

        if (user.isPresent()) {
            // Porównujemy surowe hasło z zakodowanym hasłem przy pomocy PasswordService
            return passwordService.matches(password, user.get().getPassword());  // Porównanie hasła
        }
        return false;
    }

    public boolean existsByEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public String getDefaultLanguageByUserId(String ownerId) {
        return userRepository.findById(ownerId)
                .map(User::getNativeLanguage)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
