package com.intern.hrmanagementapi.controller;

import com.intern.hrmanagementapi.constant.EndpointConst;
import com.intern.hrmanagementapi.model.ContractAddRequestDto;
import com.intern.hrmanagementapi.model.ContractUpdateRequestDto;
import com.intern.hrmanagementapi.model.DataResponseDto;
import com.intern.hrmanagementapi.service.ContractService;
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
@RequestMapping(value = {EndpointConst.Contract.BASE_PATH})
@CrossOrigin(maxAge = 3600)
@Tag(name = "Contract", description = "The contract API")
public class ContractController {

  @Autowired
  private final ContractService contractService;


  @Operation(summary = "Get list of contracts", security = {
      @SecurityRequirement(name = "bearer-key")})
  @GetMapping
  public ResponseEntity<?> getAllContract() {
    DataResponseDto res = contractService.getAll();
    return ResponseEntity.ok(res);
  }

  @Operation(summary = "Get a contract by id", security = {
      @SecurityRequirement(name = "bearer-key")})
  @GetMapping(value = {EndpointConst.Contract.GET_BY_ID})
  public ResponseEntity<?> getContractById(@PathVariable("id") UUID id) {
    var res = contractService.getById(id);
    return ResponseEntity.ok(res);

  }

  @Operation(summary = "Add contract", security = {@SecurityRequirement(name = "bearer-key")})
  @PostMapping
  public ResponseEntity<?> addContract(@Valid @RequestBody ContractAddRequestDto req) {
    var res = contractService.add(req);
    return ResponseEntity.ok(res);
  }

  @Operation(summary = "Update contract", security = {@SecurityRequirement(name = "bearer-key")})
  @PutMapping(value = {EndpointConst.Contract.UPDATE_BY_ID})
  public ResponseEntity<?> updateById(@PathVariable("id") UUID id,
      @Valid @RequestBody ContractUpdateRequestDto req) {
    var res = contractService.updateById(id, req);
    return ResponseEntity.ok(res);
  }

  @Operation(summary = "Delete contract", security = {@SecurityRequirement(name = "bearer-key")})
  @DeleteMapping(EndpointConst.Contract.DELETE_BY_ID)
  public ResponseEntity<?> deleteById(@PathVariable("id") UUID id) {
    var res = contractService.deleteById(id);
    return ResponseEntity.ok(res);
  }
}
