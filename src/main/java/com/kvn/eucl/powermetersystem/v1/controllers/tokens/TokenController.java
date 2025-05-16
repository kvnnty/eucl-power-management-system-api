package com.kvn.eucl.powermetersystem.v1.controllers.tokens;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kvn.eucl.powermetersystem.v1.dtos.requests.tokens.PurchaseTokenRequestDTO;
import com.kvn.eucl.powermetersystem.v1.dtos.responses.token.TokenResponseDTO;
import com.kvn.eucl.powermetersystem.v1.dtos.responses.token.TokenValidationDTO;
import com.kvn.eucl.powermetersystem.v1.payload.ApiResponse;
import com.kvn.eucl.powermetersystem.v1.services.tokens.TokenService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/tokens")
@RequiredArgsConstructor
@Tag(name = "Purchased Tokens resource")
public class TokenController {

  private final TokenService tokenService;

  @PostMapping("/purchase-token")
  @PreAuthorize("hasRole('CUSTOMER')")
  public ResponseEntity<ApiResponse<TokenResponseDTO>> purchase(
      @Valid @RequestBody PurchaseTokenRequestDTO requestDto) {
    return ApiResponse.success("Token purchased successfully", tokenService.purchaseElectricityToken(requestDto));
  }

  @PostMapping("/validate/{tokenNumber}")
  @PreAuthorize("hasRole('CUSTOMER')")
  public ResponseEntity<ApiResponse<TokenValidationDTO>> validateToken(@Valid @PathVariable String tokenNumber) {
    return ApiResponse.success("Token validation completed", tokenService.validateToken(tokenNumber));
  }

  @GetMapping("/meter/{meterNumber}")
  @PreAuthorize("hasRole('CUSTOMER')")
  public ResponseEntity<ApiResponse<List<TokenResponseDTO>>> getTokensByMeter(@Valid @PathVariable String meterNumber) {
    return ApiResponse.success("Tokens retrieved successfully", tokenService.getTokensByMeter(meterNumber));
  }

}
