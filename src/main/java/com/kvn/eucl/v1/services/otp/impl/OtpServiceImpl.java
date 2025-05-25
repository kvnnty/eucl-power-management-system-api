package com.kvn.eucl.v1.services.otp.impl;

import java.time.LocalDateTime;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kvn.eucl.v1.entities.users.Otp;
import com.kvn.eucl.v1.entities.users.User;
import com.kvn.eucl.v1.enums.users.OtpPurpose;
import com.kvn.eucl.v1.repositories.otp.OtpRepository;
import com.kvn.eucl.v1.repositories.users.UserRepository;
import com.kvn.eucl.v1.services.otp.OtpService;
import com.kvn.eucl.v1.utils.helpers.OtpUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OtpServiceImpl implements OtpService {

  private final OtpRepository otpRepository;
  private final UserRepository userRepository;
  private final OtpUtil otpUtil;

  @Override
  public Otp createOtp(String email, OtpPurpose purpose) {
    User user = userRepository.findByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    String code = otpUtil.generateOtp();

    Otp otp = Otp.builder()
        .code(code)
        .purpose(purpose)
        .user(user)
        .isUsed(false)
        .expiresAt(LocalDateTime.now().plusMinutes(10))
        .createdAt(LocalDateTime.now())
        .build();

    return otpRepository.save(otp);

  }

  public void validateOtp(String email, String code, OtpPurpose purpose) {
    User user = userRepository.findByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    Otp otp = otpRepository.findTopByUserAndPurposeOrderByCreatedAtDesc(user, purpose)
        .orElseThrow(() -> new IllegalArgumentException("OTP not found or expired"));

    if (otp.isUsed() || otp.getExpiresAt().isBefore(LocalDateTime.now()) || !otp.getCode().equals(code)) {
      throw new IllegalArgumentException("Invalid or expired OTP");
    }

    otp.setUsed(true);
    otpRepository.save(otp);
  }

}
