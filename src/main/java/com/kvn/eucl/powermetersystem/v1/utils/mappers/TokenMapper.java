package com.kvn.eucl.powermetersystem.v1.utils.mappers;

import com.kvn.eucl.powermetersystem.v1.dtos.responses.token.TokenResponseDTO;
import com.kvn.eucl.powermetersystem.v1.entities.tokens.PurchasedToken;
import com.kvn.eucl.powermetersystem.v1.utils.helpers.TokenUtil;

public class TokenMapper {
  public static TokenResponseDTO toDto(PurchasedToken token, TokenUtil tokenUtil) {
    TokenResponseDTO dto = MapperUtil.map(token, TokenResponseDTO.class);
    dto.setToken(tokenUtil.formatToken(token.getToken()));
    return dto;
  }
}
