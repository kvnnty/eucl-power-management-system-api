package com.kvn.eucl.powermetersystem.v1.repositories.tokens;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kvn.eucl.powermetersystem.v1.entities.tokens.PurchasedToken;

@Repository
public interface TokenRepository extends JpaRepository<PurchasedToken, UUID> {

}