package com.kvn.eucl.powermetersystem.v1.entities.tokens;

import java.time.LocalDateTime;
import java.util.UUID;

import com.kvn.eucl.powermetersystem.v1.audits.Auditable;
import com.kvn.eucl.powermetersystem.v1.entities.meters.Meter;
import com.kvn.eucl.powermetersystem.v1.enums.tokens.ETokenStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "purchased_tokens")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
public class PurchasedToken extends Auditable {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @ManyToOne
  @JoinColumn(name = "meter_id", nullable = false)
  private Meter meter;

  @Column(name = "token", unique = true, nullable = false, length = 16)
  private String token;

  @Enumerated(EnumType.STRING)
  @Column(name = "token_status", nullable = false)
  private ETokenStatus tokenStatus;

  @Column(name = "token_value_days", nullable = false)
  private Integer tokenValueDays;

  @Column(name = "purchased_date", nullable = false)
  private LocalDateTime purchasedDate;

  @Column
  private Integer amount;

}