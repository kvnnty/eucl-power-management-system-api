package com.kvn.eucl.powermetersystem.v1.repositories.tokens;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kvn.eucl.powermetersystem.v1.entities.meters.Meter;
import com.kvn.eucl.powermetersystem.v1.entities.tokens.PurchasedToken;

@Repository
public interface TokenRepository extends JpaRepository<PurchasedToken, UUID> {
  boolean existsByToken(String token);

  List<PurchasedToken> findByMeter(Meter meter);

  Optional<PurchasedToken> findByToken(String token);
}