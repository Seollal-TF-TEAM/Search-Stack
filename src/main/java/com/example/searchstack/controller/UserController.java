package com.example.searchstack.controller;


import com.example.searchstack.domain.User;
import com.example.searchstack.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Controller
public class UserController {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

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

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User request) {
        try {
            userService.registerUser(request.getUsername(), request.getEmail(), request.getPassword());
            return ResponseEntity.ok("User registered successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/dologin")
    public ResponseEntity<String> dologin(@RequestBody User request, HttpSession session) {
        try {
            // Spring Security를 통한 인증
            Authentication authentication;
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            // 인증된 사용자 정보를 가져와서 세션에 저장
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            session.setAttribute("user", userDetails);

            return ResponseEntity.ok("Login successful");
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate(); // 세션 무효화 (로그아웃)
        return ResponseEntity.ok("Logged out successfully");
    }


}
