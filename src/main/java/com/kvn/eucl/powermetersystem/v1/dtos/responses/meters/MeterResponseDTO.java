package com.kvn.eucl.powermetersystem.v1.dtos.responses.meters;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MeterResponseDTO {
  private UUID id;
  private String meterNumber;
}
