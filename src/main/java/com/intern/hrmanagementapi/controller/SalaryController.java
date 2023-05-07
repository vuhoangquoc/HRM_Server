package com.intern.hrmanagementapi.controller;

import com.intern.hrmanagementapi.constant.EndpointConst;
import com.intern.hrmanagementapi.model.DataResponseDto;
import com.intern.hrmanagementapi.model.SalaryAddRequestDto;
import com.intern.hrmanagementapi.model.SalaryUpdateRequestDto;
import com.intern.hrmanagementapi.service.SalaryService;
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
@RequestMapping(value = {EndpointConst.Salary.BASE_PATH})
@CrossOrigin(maxAge = 3600)
@Tag(name = "Salary", description = "The salary API")
public class SalaryController {

  @Autowired
  private final SalaryService salaryService;


  @Operation(summary = "Get list of Salarys", security = {
      @SecurityRequirement(name = "bearer-key")})
  @GetMapping
  public ResponseEntity<?> getAllSalary() {
    DataResponseDto res = salaryService.getAll();
    return ResponseEntity.ok(res);
  }

  @Operation(summary = "Get a Salary by id", security = {@SecurityRequirement(name = "bearer-key")})
  @GetMapping(value = {EndpointConst.Salary.GET_BY_ID})
  public ResponseEntity<?> getSalaryById(@PathVariable("id") UUID id) {
    var res = salaryService.getById(id);
    return ResponseEntity.ok(res);

  }

  @Operation(summary = "Add Salary", security = {@SecurityRequirement(name = "bearer-key")})
  @PostMapping
  public ResponseEntity<?> addSalary(@Valid @RequestBody SalaryAddRequestDto req) {
    var res = salaryService.add(req);
    return ResponseEntity.ok(res);
  }

  @Operation(summary = "Update Salary", security = {@SecurityRequirement(name = "bearer-key")})
  @PutMapping(value = {EndpointConst.Salary.UPDATE_BY_ID})
  public ResponseEntity<?> updateById(@PathVariable("id") UUID id,
      @Valid @RequestBody SalaryUpdateRequestDto req) {
    var res = salaryService.updateById(id, req);
    return ResponseEntity.ok(res);
  }

  @Operation(summary = "Delete Salary", security = {@SecurityRequirement(name = "bearer-key")})
  @DeleteMapping(EndpointConst.Salary.DELETE_BY_ID)
  public ResponseEntity<?> deleteById(@PathVariable("id") UUID id) {
    var res = salaryService.deleteById(id);
    return ResponseEntity.ok(res);
  }
}
