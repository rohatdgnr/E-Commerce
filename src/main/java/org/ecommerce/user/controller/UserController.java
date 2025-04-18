package org.ecommerce.user.controller;


import org.ecommerce.core.exceptions.ResourceAlreadyExistsException;
import org.ecommerce.core.exceptions.ResourceNotFoundException;
import org.ecommerce.core.utils.api.ApiResponse;
import org.ecommerce.user.model.dto.UserDTO;
import org.ecommerce.user.model.dto.request.CreateUserRequest;
import org.ecommerce.user.model.dto.request.UpdateUserRequest;
import org.ecommerce.user.model.entity.User;
import org.ecommerce.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("${api.prefix}/users")
@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getUsers() {
        try {
            List<User> users = userService.getUsers();
            List<UserDTO> userDtos = new ArrayList<>();
            for (User user : users) {
                userDtos.add(userService.convertToDto(user));
            }
            return ResponseEntity.ok(new ApiResponse("Success for get users request", userDtos));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse("An error occured when getting users", e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable Long id) {
        try {
            User user = userService.getUserById(id);
            UserDTO userDto = userService.convertToDto(user);
            return ResponseEntity.ok(new ApiResponse("Success for get user by id request", userDto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse("An error occured when getting user by id", e.getMessage()));
        }
    }

    @PostMapping()
    public ResponseEntity<ApiResponse> saveUser(@RequestBody CreateUserRequest request) {
        try {
            User user = userService.saveUser(request);
            UserDTO userDto = userService.convertToDto(user);
            return ResponseEntity.ok(new ApiResponse("Success for create user request", userDto));
        } catch (ResourceAlreadyExistsException e) {
            return ResponseEntity.status(409).body(new ApiResponse("Resource already exists when create user request", null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse("An error occured when saving user", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateUser(@PathVariable Long id, @RequestBody UpdateUserRequest request) {
        try {
            User user = userService.updateUser(request, id);
            UserDTO userDto = userService.convertToDto(user);
            return ResponseEntity.ok(new ApiResponse("Success for update user request", userDto));
        } catch (ResourceNotFoundException e){
            return ResponseEntity.status(404).body(new ApiResponse("User not found when update user request", null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse("An error occured when updating user", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long id) {
        try{
            userService.deleteUser(id);
            return ResponseEntity.ok(new ApiResponse("Success for delete user request", null));
        } catch (ResourceNotFoundException e){
            return ResponseEntity.status(404).body(new ApiResponse("User not found when delete user request", null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse("An error occured when deleting user", e.getMessage()));
        }
    }


}
