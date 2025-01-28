package com.example.searchstack.controller;


import com.example.searchstack.domain.User;
import com.example.searchstack.repository.UserRepository;
import com.example.searchstack.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Optional;

@Controller
public class UserController {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private UserRepository userRepository;

    public UserController(UserService userService, AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() {
        return "user/signin";  // ✅ "templates/user/signin.html"을 찾아서 렌더링
    }

    @GetMapping("/signup")
    public String signip() {
        return "user/signup";
    }

    @GetMapping("/main")
    public String main() {
        return "index";
    }

    @PostMapping("/signup")
    public ResponseEntity<String> register(@ModelAttribute User request) {
        try {
            userService.registerUser(request.getUsername(), request.getEmail(), request.getPassword());
            return ResponseEntity.ok("User registered successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/dologin")
    public ResponseEntity<String> dologin(@RequestParam String username,
                                          @RequestParam String password) {
        try {
            Optional<User> user = userRepository.findByUsername(username);

            System.out.println("🔍 입력된 패스워드: " + password);
            System.out.println("🔍 DB에서 가져온 패스워드: " + user.toString());


            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password, Collections.emptyList())
            );

            if (authentication.isAuthenticated()) {
                return ResponseEntity.ok("Login successful");
            } else {
                return ResponseEntity.status(401).body("Invalid credentials");
            }
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body("Login failed: " + e.getMessage());
        }
    }


    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate(); // 세션 무효화 (로그아웃)
        return ResponseEntity.ok("Logged out successfully");
    }




}
