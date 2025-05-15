package com.kvn.eucl.powermetersystem.v1.entities.users;

import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kvn.eucl.powermetersystem.v1.audits.Auditable;
import com.kvn.eucl.powermetersystem.v1.entities.meters.Meter;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
public class User extends Auditable {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = "names", nullable = false)
  private String names;

  @Column(name = "email", unique = true, nullable = false)
  private String email;

  @JsonIgnore
  @Column(name = "password", nullable = false)
  private String password;

  @Column(name = "phone_number", unique = true, nullable = false)
  private String phoneNumber;

  @Column(name = "national_id", unique = true, nullable = false)
  private String nationalId;

  @OneToOne
  private Role role;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  private Set<Meter> meters;

}
