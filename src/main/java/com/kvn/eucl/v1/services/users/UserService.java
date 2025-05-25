package com.kvn.eucl.v1.services.users;

import com.kvn.eucl.v1.dtos.requests.user.CreateUserDTO;
import com.kvn.eucl.v1.entities.users.User;

public interface UserService {
  void registerCustomer(CreateUserDTO requestDTO);

  User getCurrentUser();
}
