package com.kvn.eucl.powermetersystem.v1.dtos.responses;

import java.util.UUID;

import com.kvn.eucl.powermetersystem.v1.enums.users.ERole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {
  private UUID id;
  private String names;
  private String email;
  private String phoneNumber;
  private String nationalId;
  private ERole role;
}
