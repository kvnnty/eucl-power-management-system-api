package com.kvn.eucl.powermetersystem.v1.services.meter;

import java.util.List;

import com.kvn.eucl.powermetersystem.v1.dtos.requests.meters.MeterRegistrationRequestDTO;
import com.kvn.eucl.powermetersystem.v1.dtos.responses.meters.MeterResponseDTO;

public interface MeterService {
  MeterResponseDTO registerMeter(MeterRegistrationRequestDTO requestDTO);

  List<MeterResponseDTO> getAllMeters();

  List<MeterResponseDTO> getMyMeters();
}
