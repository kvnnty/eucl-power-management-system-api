package com.kvn.eucl.powermetersystem.v1.services.auth;

import com.kvn.eucl.powermetersystem.v1.dtos.requests.auth.LoginRequestDTO;
import com.kvn.eucl.powermetersystem.v1.dtos.responses.auth.AuthResponseDTO;

public interface AuthService {
  AuthResponseDTO login(LoginRequestDTO requestDTO);

  void verifyAccount(String email, String code);

  void resendAccountVerificationCode(String email);

  void forgotPassword(String email);

  void resetPassword(String email, String code, String newPassword);

  void resendPasswordResetCode(String email);

}
