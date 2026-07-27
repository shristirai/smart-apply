package com.smartapply.smart_apply.service;

import com.smartapply.smart_apply.dto.response.UserResponse;

public interface UserService {
    UserResponse getUserProfile(String email);
}