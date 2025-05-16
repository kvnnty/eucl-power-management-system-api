package com.kvn.eucl.powermetersystem.v1.dtos.requests.meters;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MeterRegistrationRequestDTO {
  @NotNull(message = "Customer ID is required")
  private UUID customerId;
}
