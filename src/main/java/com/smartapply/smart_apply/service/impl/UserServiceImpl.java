package com.smartapply.smart_apply.service.impl;
import com.smartapply.smart_apply.dto.response.UserResponse;
import com.smartapply.smart_apply.exception.SmartApplyErrorMessage;
import com.smartapply.smart_apply.exception.SmartApplyException;
import com.smartapply.smart_apply.model.User;
import com.smartapply.smart_apply.repository.UserRepository;
import com.smartapply.smart_apply.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserResponse getUserProfile(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new SmartApplyException(
                                SmartApplyErrorMessage.USER_NOT_FOUND
                        )
                );
        return new UserResponse(user.getId(), user.getFullName(), user.getEmail(), user.getRole());
    }
}