package com.likelion.coremodule.user.application;

import com.likelion.coremodule.user.domain.User;
import com.likelion.coremodule.user.exception.UserErrorCode;
import com.likelion.coremodule.user.exception.UserException;
import com.likelion.coremodule.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserQueryService {

    private final UserRepository userRepository;

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UserException(UserErrorCode.No_USER_INFO));
    }

    public User findById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new UserException(UserErrorCode.No_USER_INFO));
    }
}
