package com.kvn.eucl.powermetersystem.v1.services.tokens.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.kvn.eucl.powermetersystem.v1.dtos.requests.tokens.PurchaseTokenRequestDTO;
import com.kvn.eucl.powermetersystem.v1.dtos.responses.token.TokenResponseDTO;
import com.kvn.eucl.powermetersystem.v1.dtos.responses.token.TokenValidationDTO;
import com.kvn.eucl.powermetersystem.v1.entities.meters.Meter;
import com.kvn.eucl.powermetersystem.v1.entities.tokens.PurchasedToken;
import com.kvn.eucl.powermetersystem.v1.enums.tokens.ETokenStatus;
import com.kvn.eucl.powermetersystem.v1.exceptions.BadRequestException;
import com.kvn.eucl.powermetersystem.v1.exceptions.ResourceNotFoundException;
import com.kvn.eucl.powermetersystem.v1.repositories.meters.MeterRepository;
import com.kvn.eucl.powermetersystem.v1.repositories.tokens.TokenRepository;
import com.kvn.eucl.powermetersystem.v1.services.tokens.TokenService;
import com.kvn.eucl.powermetersystem.v1.services.users.UserService;
import com.kvn.eucl.powermetersystem.v1.utils.helpers.TokenUtil;
import com.kvn.eucl.powermetersystem.v1.utils.mappers.TokenMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

  private final MeterRepository meterRepository;
  private final TokenRepository tokenRepository;
  private final UserService userService;
  private final TokenUtil tokenUtil;

  @Override
  public TokenResponseDTO purchaseElectricityToken(PurchaseTokenRequestDTO requestDTO) {
    Integer amount = requestDTO.getAmount();
    String meterNumber = requestDTO.getMeterNumber();

    if (amount < 100) {
      throw new BadRequestException("Minimum purchase is 100 RWF.");
    }
    if (amount % 100 != 0) {
      throw new BadRequestException("Amount must be in multiples of 100 RWF. Partial-day tokens are not allowed.");
    }

    int days = (int) (amount / 100);
    if (days > 1825) {
      throw new BadRequestException("Maximum token purchase allowed is 5 years (1825 days).");
    }

    Meter meter = meterRepository.findByMeterNumber(meterNumber)
        .orElseThrow(() -> new ResourceNotFoundException("Invalid meter number. Please check and try again."));

    if (!meter.getOwner().getId().equals(userService.getCurrentUser().getId())) {
      throw new BadRequestException("You can only purchase tokens for your own meters.");
    }

    String token = tokenUtil.generateUniqueToken();

    PurchasedToken purchasedToken = PurchasedToken.builder()
        .meter(meter)
        .token(token)
        .tokenStatus(ETokenStatus.NEW)
        .tokenValueDays(days)
        .amount(amount)
        .purchasedDate(LocalDateTime.now())
        .build();

    tokenRepository.save(purchasedToken);

    return TokenMapper.toDto(purchasedToken, tokenUtil);
  }

  @Override
  public List<TokenResponseDTO> getTokensByMeter(String meterNumber) {
    Meter meter = meterRepository.findByMeterNumber(meterNumber)
        .orElseThrow(() -> new ResourceNotFoundException("Meter not found."));

    List<PurchasedToken> tokens = tokenRepository.findByMeter(meter);

    return tokens.stream()
        .map(token -> TokenMapper.toDto(token, tokenUtil))
        .toList();
  }

  @Override
  public TokenValidationDTO validateToken(String tokenNumber) {
    String rawToken = tokenNumber.replace("-", "");

    PurchasedToken token = tokenRepository.findByToken(rawToken)
        .orElseThrow(() -> new ResourceNotFoundException("Invalid token."));

    return TokenValidationDTO.builder()
        .token(tokenUtil.formatToken(token.getToken()))
        .days(token.getTokenValueDays())
        .status(token.getTokenStatus())
        .amount(token.getAmount())
        .build();
  }

}
