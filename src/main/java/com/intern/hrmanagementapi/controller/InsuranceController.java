package com.intern.hrmanagementapi.controller;

import com.intern.hrmanagementapi.constant.EndpointConst;
import com.intern.hrmanagementapi.model.DataResponseDto;
import com.intern.hrmanagementapi.model.InsuranceAddRequestDto;
import com.intern.hrmanagementapi.model.InsuranceUpdateRequestDto;
import com.intern.hrmanagementapi.service.InsuranceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin(maxAge = 3600)
@RequestMapping(value = {EndpointConst.Insurance.BASE_PATH})
@Tag(name = "Insurance", description = "The insurance API")
public class InsuranceController {

  @Autowired
  private final InsuranceService insuranceService;


  @Operation(summary = "Get list of insurances", security = {
      @SecurityRequirement(name = "bearer-key")})
  @GetMapping
  public ResponseEntity<?> getAllInsurance() {
    DataResponseDto res = insuranceService.getAll();
    return ResponseEntity.ok(res);
  }

  @Operation(summary = "Get a insurance by id", security = {
      @SecurityRequirement(name = "bearer-key")})
  @GetMapping(value = {EndpointConst.Insurance.GET_BY_ID})
  public ResponseEntity<?> getInsuranceById(@PathVariable("id") UUID id) {
    var res = insuranceService.getById(id);
    return ResponseEntity.ok(res);

  }

  @Operation(summary = "Add insurance", security = {@SecurityRequirement(name = "bearer-key")})
  @PostMapping
  public ResponseEntity<?> addInsurance(@Valid @RequestBody InsuranceAddRequestDto req) {
    var res = insuranceService.add(req);
    return ResponseEntity.ok(res);
  }

  @Operation(summary = "Update insurance", security = {@SecurityRequirement(name = "bearer-key")})
  @PutMapping(value = {EndpointConst.Insurance.UPDATE_BY_ID})
  public ResponseEntity<?> updateById(@PathVariable("id") UUID id,
      @Valid @RequestBody InsuranceUpdateRequestDto req) {
    var res = insuranceService.updateById(id, req);
    return ResponseEntity.ok(res);
  }

  @Operation(summary = "Delete insurance", security = {@SecurityRequirement(name = "bearer-key")})
  @DeleteMapping(EndpointConst.Insurance.DELETE_BY_ID)
  public ResponseEntity<?> deleteById(@PathVariable("id") UUID id) {
    var res = insuranceService.deleteById(id);
    return ResponseEntity.ok(res);
  }
}
