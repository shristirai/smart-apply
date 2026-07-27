package com.smartapply.smart_apply.service.impl;

import com.smartapply.smart_apply.dto.request.LoginRequest;
import com.smartapply.smart_apply.dto.request.RegisterRequest;
import com.smartapply.smart_apply.dto.response.AuthResponse;
import com.smartapply.smart_apply.model.User;
import com.smartapply.smart_apply.repository.UserRepository;
import com.smartapply.smart_apply.security.JwtService;
import com.smartapply.smart_apply.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        String role = request.getRole().toUpperCase();
        if (!role.equals("SEEKER") && !role.equals("RECRUITER")) {
            throw new RuntimeException("Role must be SEEKER or RECRUITER");
        }

        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(role);
        userRepository.save(user);

        String token = jwtService.generateToken(user.getEmail());
        return new AuthResponse(token, user.getEmail(),
                user.getFullName(), user.getRole());
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(), request.getPassword()));

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtService.generateToken(user.getEmail());
        return new AuthResponse(token, user.getEmail(),
                user.getFullName(), user.getRole());
    }
}