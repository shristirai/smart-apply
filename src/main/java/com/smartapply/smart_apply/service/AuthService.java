package com.smartapply.smart_apply.service;

import com.smartapply.smart_apply.dto.request.LoginRequest;
import com.smartapply.smart_apply.dto.request.RegisterRequest;
import com.smartapply.smart_apply.dto.response.AuthResponse;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
}