package com.kvn.eucl.v1.dtos.responses.token;

import com.kvn.eucl.v1.enums.tokens.ETokenStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenValidationDTO {
  private String token;
  private Integer days;
  private Integer amount;
  private ETokenStatus status;
}
