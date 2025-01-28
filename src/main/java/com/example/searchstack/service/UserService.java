package com.example.searchstack.service;

import com.example.searchstack.domain.User;
import com.example.searchstack.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(user -> org.springframework.security.core.userdetails.User.builder()
                        .username(user.getUsername())
                        .password(user.getPassword()) // Bcrypt 암호화된 비밀번호
                        .roles(user.getRole()) // role 사용
                        .build()
                ).orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    public User registerUser(String username, String email, String password) {
        // 중복 검사 (username & email)
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(password);

        // 새로운 사용자 저장
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(encodedPassword);
        user.setRole("USER"); // 기본 ROLE 설정

        return userRepository.save(user);
    }

}