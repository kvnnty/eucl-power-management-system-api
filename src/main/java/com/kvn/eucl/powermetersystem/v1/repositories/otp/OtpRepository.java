package com.kvn.eucl.powermetersystem.v1.repositories.otp;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kvn.eucl.powermetersystem.v1.entities.users.Otp;
import com.kvn.eucl.powermetersystem.v1.entities.users.User;
import com.kvn.eucl.powermetersystem.v1.enums.users.OtpPurpose;

@Repository
public interface OtpRepository extends JpaRepository<Otp, UUID> {
  boolean existsByCode(String code);

  Optional<Otp> findTopByUserAndPurposeOrderByCreatedAtDesc(User user, OtpPurpose purpose);

  Optional<Otp> findByUserAndPurposeAndCodeAndIsUsedIsFalse(User user, OtpPurpose purpose, String code);

  List<Otp> findAllByUserAndPurposeAndIsUsedFalse(User user, OtpPurpose purpose);
}
