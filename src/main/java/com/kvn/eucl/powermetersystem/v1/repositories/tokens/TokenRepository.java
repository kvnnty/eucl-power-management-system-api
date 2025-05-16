package com.kvn.eucl.powermetersystem.v1.repositories.tokens;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.kvn.eucl.powermetersystem.v1.entities.meters.Meter;
import com.kvn.eucl.powermetersystem.v1.entities.tokens.PurchasedToken;
import com.kvn.eucl.powermetersystem.v1.enums.tokens.ETokenStatus;

@Repository
public interface TokenRepository extends JpaRepository<PurchasedToken, UUID> {
  boolean existsByToken(String token);

  List<PurchasedToken> findByMeter(Meter meter);

  Optional<PurchasedToken> findByToken(String token);

  @Query("SELECT t FROM PurchasedToken t WHERE t.tokenStatus = 'NEW' AND t.expirationDate BETWEEN :now AND :limit")
  List<PurchasedToken> findTokensExpiringInWindow(@Param("now") LocalDateTime now, @Param("limit") LocalDateTime limit);

  List<PurchasedToken> findByTokenStatus(ETokenStatus tokenStatus);

}