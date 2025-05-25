package com.kvn.eucl.v1.utils.helpers;

import java.util.Random;

import org.springframework.stereotype.Component;

import com.kvn.eucl.v1.repositories.meters.MeterRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MeterUtil {
  private final MeterRepository meterRepository;

  public String generateUniqueMeterNumber() {
    String number;
    do {
      number = String.format("%06d", new Random().nextInt(1_000_000));
    } while (meterRepository.existsByMeterNumber(number));
    return number;
  }
}
