package com.kvn.eucl.powermetersystem.v1.controllers.meters;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kvn.eucl.powermetersystem.v1.dtos.requests.meters.MeterRegistrationRequestDTO;
import com.kvn.eucl.powermetersystem.v1.dtos.responses.meters.MeterResponseDTO;
import com.kvn.eucl.powermetersystem.v1.payload.ApiResponse;
import com.kvn.eucl.powermetersystem.v1.services.meter.MeterService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/meters")
@RequiredArgsConstructor
@Tag(name = "Meter Resource")
public class MeterController {
  private final MeterService meterService;

  @PostMapping("/register")
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  public ResponseEntity<ApiResponse<MeterResponseDTO>> registerMeter(
      @RequestBody @Valid MeterRegistrationRequestDTO requestDto) {
    MeterResponseDTO meter = meterService.registerMeter(requestDto);
    return ApiResponse.success("Meter registered successfully", HttpStatus.CREATED, meter);

  }

  @GetMapping
  @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_SUPER_ADMIN')")
  public ResponseEntity<ApiResponse<List<MeterResponseDTO>>> getAllMeters() {
    List<MeterResponseDTO> meters = meterService.getAllMeters();
    return ApiResponse.success("All Meters retrieved successfully", HttpStatus.CREATED, meters);
  }

  @GetMapping("/my-meters")
  @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
  public ResponseEntity<ApiResponse<List<MeterResponseDTO>>> getMyMeters() {
    List<MeterResponseDTO> meters = meterService.getMyMeters();
    return ApiResponse.success("My Meters retrieved successfully", HttpStatus.CREATED, meters);
  }
}
