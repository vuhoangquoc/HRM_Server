package com.intern.hrmanagementapi.controller;

import com.intern.hrmanagementapi.constant.EndpointConst;
import com.intern.hrmanagementapi.model.DataResponseDto;
import com.intern.hrmanagementapi.model.DepartmentRequestDto;
import com.intern.hrmanagementapi.service.DepartmentService;
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
@RequestMapping(value = {EndpointConst.Department.BASE_PATH})
@CrossOrigin(maxAge = 3600)
@Tag(name = "Department", description = "The department API")
public class DepartmentController {

  @Autowired
  private final DepartmentService departmentService;


  @Operation(summary = "Get list of departments", security = {
      @SecurityRequirement(name = "bearer-key")})
  @GetMapping
  public ResponseEntity<?> getAllDepartment() {
    DataResponseDto res = departmentService.getAll();
    return ResponseEntity.ok(res);
  }

  @Operation(summary = "Get a department by id", security = {
      @SecurityRequirement(name = "bearer-key")})
  @GetMapping(value = {EndpointConst.Department.GET_BY_ID})
  public ResponseEntity<?> getDepartmentById(@PathVariable("id") UUID id) {
    var res = departmentService.getById(id);
    return ResponseEntity.ok(res);

  }

  @Operation(summary = "Add department", security = {@SecurityRequirement(name = "bearer-key")})
  @PostMapping
  public ResponseEntity<?> addDepartment(@Valid @RequestBody DepartmentRequestDto req) {
    var res = departmentService.add(req);
    return ResponseEntity.ok(res);
  }

  @Operation(summary = "Update department", security = {@SecurityRequirement(name = "bearer-key")})
  @PutMapping(value = {EndpointConst.Department.UPDATE_BY_ID})
  public ResponseEntity<?> updateById(@PathVariable("id") UUID id,
      @Valid @RequestBody DepartmentRequestDto req) {
    var res = departmentService.updateById(id, req);
    return ResponseEntity.ok(res);
  }

  @Operation(summary = "Delete department", security = {@SecurityRequirement(name = "bearer-key")})
  @DeleteMapping(EndpointConst.Department.DELETE_BY_ID)
  public ResponseEntity<?> deleteById(@PathVariable("id") UUID id) {
    var res = departmentService.deleteById(id);
    return ResponseEntity.ok(res);
  }
}
