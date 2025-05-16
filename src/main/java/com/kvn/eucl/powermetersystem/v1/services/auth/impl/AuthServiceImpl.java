package com.kvn.eucl.powermetersystem.v1.services.auth.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.kvn.eucl.powermetersystem.v1.dtos.requests.auth.LoginRequestDTO;
import com.kvn.eucl.powermetersystem.v1.dtos.responses.auth.AuthResponseDTO;
import com.kvn.eucl.powermetersystem.v1.entities.users.User;
import com.kvn.eucl.powermetersystem.v1.exceptions.BadRequestException;
import com.kvn.eucl.powermetersystem.v1.security.jwt.JwtTokenService;
import com.kvn.eucl.powermetersystem.v1.services.auth.AuthService;
import com.kvn.eucl.powermetersystem.v1.services.users.UserService;
import com.kvn.eucl.powermetersystem.v1.utils.mappers.UserMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final AuthenticationManager authenticationManager;
  private final UserService userService;
  private final JwtTokenService jwtTokenService;

  @Override
  public AuthResponseDTO login(LoginRequestDTO requestDTO) {
    Authentication authentication = authentication(requestDTO);

    if (!authentication.isAuthenticated()) {
      throw new BadRequestException("Couldn't authenticate request");
    }

    User user = userService.getCurrentUser();
    return AuthResponseDTO.builder()
        .user(UserMapper.toDto(user))
        .token(generateJwtToken(authentication))
        .build();

  }

  @Override
  public void forgotPassword(String email) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'forgotPassword'");
  }

  @Override
  public void verifyAccount(String email) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'verifyAccount'");
  }

  @Override
  public void resendAccountVerificationCode(String email) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'resendAccountVerificationCode'");
  }

  private Authentication authentication(LoginRequestDTO requestDTO) {
    try {
      UsernamePasswordAuthenticationToken authenticationRequest = new UsernamePasswordAuthenticationToken(
          requestDTO.getEmail(), requestDTO.getPassword());
      Authentication authentication = authenticationManager.authenticate(authenticationRequest);
      SecurityContextHolder.getContext().setAuthentication(authentication);
      return authentication;

    } catch (AuthenticationException e) {
      throw new BadCredentialsException("Incorrect email or password");
    }
  }

  private String generateJwtToken(Authentication authentication) {
    return jwtTokenService.generateToken(authentication);
  }

}
