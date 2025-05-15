package com.kvn.eucl.powermetersystem.v1.repositories.roles;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kvn.eucl.powermetersystem.v1.entities.users.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {

}
