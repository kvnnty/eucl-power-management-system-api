package com.kvn.eucl.powermetersystem.v1.entities.users;

import java.util.UUID;

import com.kvn.eucl.powermetersystem.v1.enums.users.ERole;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

@Entity
@Table(name = "roles")
@Data
@Builder
public class Role {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Enumerated(EnumType.STRING)
  @Column(name = "role_type", unique = true, nullable = false)
  private ERole type;
}
