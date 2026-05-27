package ir.aspireapps.springmart.service;

import ir.aspireapps.springmart.dto.user.UserRegistrationRequest;
import ir.aspireapps.springmart.dto.user.UserResponse;
import ir.aspireapps.springmart.dto.user.UserUpdateDetailsRequest;
import ir.aspireapps.springmart.error.DuplicateResourceException;
import ir.aspireapps.springmart.error.RedundantActionException;
import ir.aspireapps.springmart.error.ResourceNotFoundException;
import ir.aspireapps.springmart.mapper.UserMapper;
import ir.aspireapps.springmart.model.Role;
import ir.aspireapps.springmart.model.User;
import ir.aspireapps.springmart.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Transactional
    public User register(UserRegistrationRequest userRegistrationRequest) {
        if (userRepository.existsByEmail(userRegistrationRequest.email()))
            throw new DuplicateResourceException("Email[" + userRegistrationRequest.email() + "] already exists");

        User newUser = userMapper.toEntity(userRegistrationRequest);
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        newUser.setRole(Role.USER);

        return userRepository.save(newUser);
    }

    public UserResponse get(String email) {
        return userMapper.toResponse(
                userRepository.findByEmail(email)
                        .orElseThrow(() -> new ResourceNotFoundException("Email[" + email + "] not found"))
        );
    }

    @Transactional
    public void setStatus(UUID id, boolean newStatus) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with Id[" + id + "] not found"));
        if (newStatus == user.getEnabled()) {
            throw new RedundantActionException("User with Id[" + id + "] has been already have state of [" + newStatus + "].");
        }
        user.setEnabled(newStatus);
    }

    @Transactional
    public UserResponse update(String email, UserUpdateDetailsRequest request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User with Id[" + email + "] not found"));
        user.update(request);
        return userMapper.toResponse(user);
    }
}
