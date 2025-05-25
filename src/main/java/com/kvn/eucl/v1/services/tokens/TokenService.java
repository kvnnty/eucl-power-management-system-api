package com.kvn.eucl.v1.services.tokens;

import java.util.List;

import com.kvn.eucl.v1.dtos.requests.tokens.PurchaseTokenRequestDTO;
import com.kvn.eucl.v1.dtos.responses.token.TokenResponseDTO;
import com.kvn.eucl.v1.dtos.responses.token.TokenValidationDTO;

public interface TokenService {
  TokenResponseDTO purchaseElectricityToken(PurchaseTokenRequestDTO requestDTO);

  List<TokenResponseDTO> getTokensByMeter(String meterNumber);

  TokenValidationDTO validateToken(String tokenNumber);
}
