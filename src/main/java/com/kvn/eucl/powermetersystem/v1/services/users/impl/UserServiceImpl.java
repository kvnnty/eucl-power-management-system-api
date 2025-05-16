package com.kvn.eucl.powermetersystem.v1.services.users.impl;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kvn.eucl.powermetersystem.v1.dtos.requests.user.CreateUserDTO;
import com.kvn.eucl.powermetersystem.v1.entities.users.Otp;
import com.kvn.eucl.powermetersystem.v1.entities.users.Role;
import com.kvn.eucl.powermetersystem.v1.entities.users.User;
import com.kvn.eucl.powermetersystem.v1.enums.users.ERole;
import com.kvn.eucl.powermetersystem.v1.enums.users.OtpPurpose;
import com.kvn.eucl.powermetersystem.v1.exceptions.DuplicateResourceException;
import com.kvn.eucl.powermetersystem.v1.repositories.roles.RoleRepository;
import com.kvn.eucl.powermetersystem.v1.repositories.users.UserRepository;
import com.kvn.eucl.powermetersystem.v1.security.user.UserPrincipal;
import com.kvn.eucl.powermetersystem.v1.services.mail.MailService;
import com.kvn.eucl.powermetersystem.v1.services.otp.OtpService;
import com.kvn.eucl.powermetersystem.v1.services.users.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final RoleRepository roleRepository;
  private final OtpService otpService;
  private final MailService mailService;

  @Override
  public void registerCustomer(CreateUserDTO requestDTO) {
    if (userRepository.existsByEmail(requestDTO.getEmail())) {
      throw new DuplicateResourceException("Email is already in use.");
    }
    if (userRepository.existsByPhoneNumber(requestDTO.getPhoneNumber())) {
      throw new DuplicateResourceException("Phone number is already in use.");
    }
    if (userRepository.existsByNationalId(requestDTO.getNationalId())) {
      throw new DuplicateResourceException("National ID is already registered.");
    }

    Role customerRole = roleRepository.findByType(ERole.ROLE_CUSTOMER)
        .orElseThrow(() -> new IllegalStateException("Role not found"));

    User newCustomer = User.builder()
        .names(requestDTO.getNames())
        .email(requestDTO.getEmail())
        .password(passwordEncoder.encode(requestDTO.getPassword()))
        .phoneNumber(requestDTO.getPhoneNumber())
        .nationalId(requestDTO.getNationalId())
        .isVerified(false)
        .role(customerRole)
        .build();

    userRepository.save(newCustomer);
    Otp otp = otpService.createOtp(newCustomer.getEmail(), OtpPurpose.VERIFICATION);

    String message = String.format(
        "Hello %s,\n\nUse the following code to verify your account: %s\nThis code will expire in 10 minutes.\n\nThanks,\nEUCL Team",
        newCustomer.getNames(), otp.getCode());
    mailService.sendMail(newCustomer.getEmail(), "Account Verification Code", message);
  }

  @Override
  public User getCurrentUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null || authentication.getPrincipal() == null) {
      throw new AuthenticationCredentialsNotFoundException("No authenticated user found");
    }

    UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

    User user = userRepository.findByEmail(userPrincipal.getEmail())
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    return user;
  }

}
