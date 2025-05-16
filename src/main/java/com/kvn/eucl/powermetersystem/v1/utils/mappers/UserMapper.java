package com.kvn.eucl.powermetersystem.v1.utils.mappers;

import com.kvn.eucl.powermetersystem.v1.dtos.responses.users.UserResponseDTO;
import com.kvn.eucl.powermetersystem.v1.entities.users.User;

public class UserMapper {
  public static UserResponseDTO toDto(User user) {
    return UserResponseDTO.builder()
        .id(user.getId())
        .names(user.getNames())
        .email(user.getEmail())
        .phoneNumber(user.getPhoneNumber())
        .nationalId(user.getNationalId())
        .role(user.getRole().getType())
        .build();
  }
}
