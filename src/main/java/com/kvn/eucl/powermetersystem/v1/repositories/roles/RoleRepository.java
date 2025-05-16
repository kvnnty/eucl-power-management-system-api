package com.kvn.eucl.powermetersystem.v1.repositories.roles;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kvn.eucl.powermetersystem.v1.entities.users.Role;
import com.kvn.eucl.powermetersystem.v1.enums.users.ERole;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {
  Optional<Role> findByType(ERole type);

  boolean existsByType(ERole type);
}
