package com.kvn.eucl.v1.dtos.requests.tokens;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseTokenRequestDTO {
  @NotBlank(message = "Please enter your meter number")
  @Size(min = 6, message = "Please enter a valid meter number")
  private String meterNumber;

  @NotNull(message = "Amount is required")
  @Min(6)
  private Integer amount;
}
