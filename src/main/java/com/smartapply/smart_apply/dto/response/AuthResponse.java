
package com.smartapply.smart_apply.dto.response;

import com.smartapply.smart_apply.dto.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private String email;
    private String fullName;
    private Role role;
}