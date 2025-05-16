package com.kvn.eucl.powermetersystem.v1.seed;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.kvn.eucl.powermetersystem.v1.entities.users.Role;
import com.kvn.eucl.powermetersystem.v1.entities.users.User;
import com.kvn.eucl.powermetersystem.v1.enums.users.ERole;
import com.kvn.eucl.powermetersystem.v1.repositories.roles.RoleRepository;
import com.kvn.eucl.powermetersystem.v1.repositories.users.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataSeeder implements ApplicationRunner {

  private final RoleRepository roleRepository;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Value("${application.superadmin.email}")
  private String email;

  @Value("${application.superadmin.names}")
  private String names;

  @Value("${application.superadmin.password}")
  private String password;

  @Value("${application.superadmin.phoneNumber}")
  private String phoneNumber;

  @Value("${application.superadmin.nationalId}")
  private String nationalId;

  @Override
  public void run(ApplicationArguments args) {
    seedRoles();
    seedSuperAdmin();
  }

  private void seedRoles() {
    Arrays.stream(ERole.values()).forEach(roleEnum -> {
      if (!roleRepository.existsByType(roleEnum)) {
        roleRepository.save(Role.builder().type(roleEnum).build());
      }
    });
  }

  private void seedSuperAdmin() {
    if (userRepository.findByEmail(email).isEmpty()) {
      Role superAdminRole = roleRepository.findByType(ERole.ROLE_SUPER_ADMIN)
          .orElseThrow(() -> new RuntimeException("Super admin role not found"));

      User superAdmin = User.builder()
          .email(email)
          .names(names)
          .password(passwordEncoder.encode(password))
          .phoneNumber(phoneNumber)
          .nationalId(nationalId)
          .isVerified(true)
          .role(superAdminRole)
          .build();

      userRepository.save(superAdmin);
    }
  }
}
