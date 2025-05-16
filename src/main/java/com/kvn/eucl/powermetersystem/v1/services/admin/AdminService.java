package com.kvn.eucl.powermetersystem.v1.services.admin;

import java.util.List;
import java.util.UUID;

import com.kvn.eucl.powermetersystem.v1.dtos.requests.user.CreateUserDTO;
import com.kvn.eucl.powermetersystem.v1.dtos.requests.user.UpdateUserDTO;
import com.kvn.eucl.powermetersystem.v1.dtos.responses.users.UserResponseDTO;

public interface AdminService {
  void createAdmin(CreateUserDTO requestDto);

  void deleteAdmin(UUID adminId);

  List<UserResponseDTO> getAllAdmins();

  void updateAdmin(UUID adminId, UpdateUserDTO updateUserDTO);
}
