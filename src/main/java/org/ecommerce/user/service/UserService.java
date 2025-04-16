package org.ecommerce.user.service;


import lombok.RequiredArgsConstructor;
import org.ecommerce.core.exceptions.ResourceNotFoundException;
import org.ecommerce.user.model.dto.UserDTO;
import org.ecommerce.user.model.dto.request.CreateUserRequest;
import org.ecommerce.user.model.dto.request.UpdateUserRequest;
import org.ecommerce.user.model.entity.User;
import org.ecommerce.user.repository.UserRepository;
import org.ecommerce.user.service.impl.IUserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User saveUser(CreateUserRequest request) {
        return Optional.of(request).filter(user -> !userRepository.existsByEmail(user.getEmail()))
                .map(user -> userRepository.save(User.builder()
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .email(user.getEmail())
                        .password(passwordEncoder.encode(user.getPassword()))
                        .build()))
                .orElseThrow(() -> new ResourceNotFoundException(request.getEmail() + " already exists in the system!"));
    }

    @Override
    public User updateUser(UpdateUserRequest request, Long userId) {
        return userRepository.findById(userId).map(user -> {
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            return userRepository.save(user);
        }).orElseThrow(() -> new ResourceNotFoundException("User not found when update user service!"));
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found when get user by id service!"));
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.findById(id).ifPresentOrElse(userRepository::delete, () -> {
            throw new ResourceNotFoundException("User not found when delete user by id service!");
        });
    }

    @Override
    public UserDTO convertToDto(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email);
    }

}
