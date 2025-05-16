package com.kvn.eucl.powermetersystem.v1.repositories.meters;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kvn.eucl.powermetersystem.v1.entities.meters.Meter;
import com.kvn.eucl.powermetersystem.v1.entities.users.User;

@Repository
public interface MeterRepository extends JpaRepository<Meter, UUID> {
  boolean existsByMeterNumber(String meterNumber);

  Optional<Meter> findByMeterNumber(String meterNumber);

  List<Meter> findAllByOwner(User owner);
}
