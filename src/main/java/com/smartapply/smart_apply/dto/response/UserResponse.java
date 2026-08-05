package com.smartapply.smart_apply.dto.response;

import com.smartapply.smart_apply.dto.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String fullName;
    private String email;
    private Role role;
}