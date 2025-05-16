package com.kvn.eucl.powermetersystem.v1.services.meter.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kvn.eucl.powermetersystem.v1.dtos.requests.meters.MeterRegistrationRequestDTO;
import com.kvn.eucl.powermetersystem.v1.dtos.responses.meters.MeterResponseDTO;
import com.kvn.eucl.powermetersystem.v1.entities.meters.Meter;
import com.kvn.eucl.powermetersystem.v1.entities.users.User;
import com.kvn.eucl.powermetersystem.v1.enums.users.ERole;
import com.kvn.eucl.powermetersystem.v1.exceptions.BadRequestException;
import com.kvn.eucl.powermetersystem.v1.repositories.meters.MeterRepository;
import com.kvn.eucl.powermetersystem.v1.repositories.users.UserRepository;
import com.kvn.eucl.powermetersystem.v1.services.meter.MeterService;
import com.kvn.eucl.powermetersystem.v1.services.users.UserService;
import com.kvn.eucl.powermetersystem.v1.utils.helpers.MeterUtil;
import com.kvn.eucl.powermetersystem.v1.utils.mappers.MapperUtil;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MeterServiceImpl implements MeterService {

  private final UserRepository userRepository;
  private final UserService userService;
  private final MeterRepository meterRepository;
  private final MeterUtil meterUtil;

  @Override
  public MeterResponseDTO registerMeter(MeterRegistrationRequestDTO requestDto) {

    User user = userRepository.findById(requestDto.getCustomerId())
        .orElseThrow(() -> new EntityNotFoundException("User not found"));

    if (user.getRole().getType() != ERole.ROLE_CUSTOMER) {
      throw new BadRequestException("Only customers can be assigned a meter number.");
    }

    String meterNumber = meterUtil.generateUniqueMeterNumber();

    Meter meter = Meter.builder()
        .meterNumber(meterNumber)
        .owner(user)
        .build();

    Meter newMeter = meterRepository.save(meter);
    return MapperUtil.map(newMeter, MeterResponseDTO.class);
  }

  @Override
  public List<MeterResponseDTO> getMyMeters() {
    User user = userService.getCurrentUser();
    List<Meter> meters = meterRepository.findAllByOwner(user);
    return meters.stream()
        .map(m -> MapperUtil.map(m, MeterResponseDTO.class)).toList();

  }

  @Override
  public List<MeterResponseDTO> getAllMeters() {
    return meterRepository.findAll().stream()
        .map(m -> MapperUtil.map(m, MeterResponseDTO.class)).toList();
  }

}
