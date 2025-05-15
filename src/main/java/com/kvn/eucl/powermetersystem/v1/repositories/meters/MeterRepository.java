package com.kvn.eucl.powermetersystem.v1.repositories.meters;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kvn.eucl.powermetersystem.v1.entities.meters.Meter;

@Repository
public interface MeterRepository extends JpaRepository<Meter, UUID> {
  
}
