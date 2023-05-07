package com.intern.hrmanagementapi.controller;

import com.intern.hrmanagementapi.constant.EndpointConst;
import com.intern.hrmanagementapi.model.DataResponseDto;
import com.intern.hrmanagementapi.model.DepartmentRequestDto;
import com.intern.hrmanagementapi.service.PositionService;
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
@RequestMapping(value = {EndpointConst.Position.BASE_PATH})
@CrossOrigin(maxAge = 3600)
@Tag(name = "Position", description = "The Position API")
public class PositionController {

  @Autowired
  private final PositionService positionService;


  @Operation(summary = "Get list of position", security = {
      @SecurityRequirement(name = "bearer-key")})
  @GetMapping
  public ResponseEntity<?> getAllDepartment() {
    DataResponseDto res = positionService.getAll();
    return ResponseEntity.ok(res);
  }

  @Operation(summary = "Get a position by id", security = {
      @SecurityRequirement(name = "bearer-key")})
  @GetMapping(value = {EndpointConst.Position.GET_BY_ID})
  public ResponseEntity<?> getPositionById(@PathVariable("id") UUID id) {
    var res = positionService.getById(id);
    return ResponseEntity.ok(res);

  }

  @Operation(summary = "Add position", security = {@SecurityRequirement(name = "bearer-key")})
  @PostMapping
  public ResponseEntity<?> addDepartment(@Valid @RequestBody DepartmentRequestDto req) {
    var res = positionService.add(req);
    return ResponseEntity.ok(res);
  }

  @Operation(summary = "Update position", security = {@SecurityRequirement(name = "bearer-key")})
  @PutMapping(value = {EndpointConst.Position.UPDATE_BY_ID})
  public ResponseEntity<?> updateById(@PathVariable("id") UUID id,
      @Valid @RequestBody DepartmentRequestDto req) {
    var res = positionService.updateById(id, req);
    return ResponseEntity.ok(res);
  }

  @Operation(summary = "Delete position", security = {@SecurityRequirement(name = "bearer-key")})
  @DeleteMapping(EndpointConst.Position.DELETE_BY_ID)
  public ResponseEntity<?> deleteById(@PathVariable("id") UUID id) {
    var res = positionService.deleteById(id);
    return ResponseEntity.ok(res);
  }
}
