package com.kvn.eucl.powermetersystem.v1.services.auth;

import com.kvn.eucl.powermetersystem.v1.dtos.requests.auth.LoginRequestDTO;
import com.kvn.eucl.powermetersystem.v1.dtos.responses.auth.AuthResponseDTO;

public interface AuthService {
  AuthResponseDTO login(LoginRequestDTO requestDTO);

  void forgotPassword(String email);

  void verifyAccount(String email);

  void resendAccountVerificationCode(String email);

}
