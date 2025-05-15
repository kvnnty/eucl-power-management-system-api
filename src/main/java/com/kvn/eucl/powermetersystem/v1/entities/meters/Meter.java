package com.kvn.eucl.powermetersystem.v1.entities.meters;

import java.util.UUID;

import com.kvn.eucl.powermetersystem.v1.audits.Auditable;
import com.kvn.eucl.powermetersystem.v1.entities.users.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "meters")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Setter
@Getter
public class Meter extends Auditable {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = "meter_number", unique = true, nullable = false, length = 6)
  private String meterNumber;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;
}
