package com.kvn.eucl.v1.dtos.responses.token;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenResponseDTO {
  private String token;
  private int tokenValueDays;
  private LocalDateTime purchasedDate;
  private LocalDateTime expirationDate;
  private Float amount;
}
