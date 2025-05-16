package com.kvn.eucl.powermetersystem.v1.entities.notifications;

import java.time.LocalDateTime;
import java.util.UUID;

import com.kvn.eucl.powermetersystem.v1.audits.Auditable;
import com.kvn.eucl.powermetersystem.v1.entities.meters.Meter;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@Table(name = "notifications")
@AllArgsConstructor
@NoArgsConstructor
public class Notification extends Auditable {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private String message;

  @ManyToOne
  @JoinColumn(name = "meter_id", nullable = false)
  private Meter meter;

  private LocalDateTime issuedDate;
}
