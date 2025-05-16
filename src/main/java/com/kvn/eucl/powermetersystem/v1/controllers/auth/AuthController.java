package com.kvn.eucl.powermetersystem.v1.controllers.auth;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kvn.eucl.powermetersystem.v1.dtos.requests.auth.LoginRequestDTO;
import com.kvn.eucl.powermetersystem.v1.dtos.responses.auth.AuthResponseDTO;
import com.kvn.eucl.powermetersystem.v1.payload.ApiResponse;
import com.kvn.eucl.powermetersystem.v1.services.auth.AuthService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1/auth")
@Validated
@RequiredArgsConstructor
@Tag(name = "Authentication Resource")
public class AuthController {
  private final AuthService authService;

  @PostMapping("/login")
  public ResponseEntity<ApiResponse<AuthResponseDTO>> login(@Valid @RequestBody LoginRequestDTO requestDTO) {
    AuthResponseDTO authResponse = authService.login(requestDTO);
    return ApiResponse.success("Logged in successfully", HttpStatus.OK, authResponse);
  }

  @PostMapping("/forgot-password")
  public ResponseEntity<ApiResponse<Object>> forgotPassword(@RequestBody String email) {
    authService.forgotPassword(email);
    return ApiResponse.success("Reset password instructions sent to email", HttpStatus.OK, null);
  }

  @PostMapping("/reset-password")
  public String resetPassword(@RequestBody String entity) {
    return entity;
  }

  @PostMapping("/verify-account")
  public String verifyAccount(@RequestBody String entity) {

    return entity;
  }

  @PostMapping("/resend-account-verification-code")
  public String resendAccountVerificationCode(@RequestBody String entity) {
    return entity;
  }

  @PostMapping("/resend-password-reset-code")
  public String resendPasswordResetCode(@RequestBody String entity) {
    return entity;
  }

}
