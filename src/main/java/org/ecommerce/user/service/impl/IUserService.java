package org.ecommerce.user.service.impl;


import org.ecommerce.user.model.dto.UserDTO;
import org.ecommerce.user.model.dto.request.CreateUserRequest;
import org.ecommerce.user.model.dto.request.UpdateUserRequest;
import org.ecommerce.user.model.entity.User;

import java.util.List;

public interface IUserService {
    User getUserById(Long id);
    User saveUser(CreateUserRequest request);
    User updateUser(UpdateUserRequest request, Long userId);

    List<User> getUsers();

    void deleteUser(Long id);

    UserDTO convertToDto(User user);

    User getAuthenticatedUser();
}
