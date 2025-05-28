package com.kvn.eucl.v1.controllers.users;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kvn.eucl.v1.dtos.requests.user.CreateUserDTO;
import com.kvn.eucl.v1.payload.ApiResponse;
import com.kvn.eucl.v1.services.users.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "User Controller", description = "APIs for managing user account related operations")
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;

  @PostMapping("/register")
  public ResponseEntity<ApiResponse<Object>> registerCustomer(@Valid @RequestBody CreateUserDTO requestDTO) {
    userService.registerCustomer(requestDTO);
    return ApiResponse.success("Account created successfully, please check your email to verify your account",
        HttpStatus.CREATED, null);
  }

}