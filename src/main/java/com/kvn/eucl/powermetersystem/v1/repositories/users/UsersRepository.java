package com.kvn.eucl.powermetersystem.v1.repositories.users;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kvn.eucl.powermetersystem.v1.entities.users.User;

@Repository
public interface UsersRepository extends JpaRepository<User, UUID> {
  Optional<User> findByEmail(String email);

  Optional<User> findByNationalId(String nationalId);

  Optional<User> findByPhoneNumber(String phoneNumber);
}
