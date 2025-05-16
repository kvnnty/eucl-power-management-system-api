package com.kvn.eucl.powermetersystem.v1.services.otp;

import com.kvn.eucl.powermetersystem.v1.entities.users.Otp;
import com.kvn.eucl.powermetersystem.v1.enums.users.OtpPurpose;

public interface OtpService {
  Otp createOtp(String email, OtpPurpose otpPurpose);

  void validateOtp(String email, String code, OtpPurpose purpose);
}
