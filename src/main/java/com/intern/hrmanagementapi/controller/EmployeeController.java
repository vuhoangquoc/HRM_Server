package com.intern.hrmanagementapi.controller;

import com.intern.hrmanagementapi.constant.EndpointConst;
import com.intern.hrmanagementapi.constant.MessageConst;
import com.intern.hrmanagementapi.entity.EmployeeEntity;
import com.intern.hrmanagementapi.exception.EmployeeNotFoundException;
import com.intern.hrmanagementapi.model.DataResponseDto;
import com.intern.hrmanagementapi.model.EmployeeRequestDto;
import com.intern.hrmanagementapi.service.EmployeeService;
import com.intern.hrmanagementapi.util.EmployeeExportToExcel;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
@RequestMapping(value = {EndpointConst.Employee.BASE_PATH})
@CrossOrigin(maxAge = 3600)
@Tag(name = "Employee", description = "The employee API")
public class EmployeeController {

  @Autowired
  private EmployeeService employeeService;
  @Autowired
  private EmployeeExportToExcel employeeExportToExcel;

  @Operation(summary = "List all employees", security = {@SecurityRequirement(name = "bearer-key")})
  @GetMapping
  public ResponseEntity<?> getAllEmployee() {
    var response = employeeService.getAllEmployeesByUserId();
    return ResponseEntity.ok(
        DataResponseDto.success(HttpStatus.OK.value(), MessageConst.SUCCESS, response));
  }

  @Operation(summary = "Get employee by id", security = {@SecurityRequirement(name = "bearer-key")})
  @GetMapping(EndpointConst.Employee.GET_BY_ID)
  public ResponseEntity<?> getEmployeeById(@PathVariable UUID id) {
    var response = employeeService.getEmployeeByIdAndUserId(id);
    return ResponseEntity.ok(
        DataResponseDto.success(HttpStatus.OK.value(), MessageConst.SUCCESS, response));
  }

  @Operation(summary = "Add employee", security = {@SecurityRequirement(name = "bearer-key")})
  @PostMapping
  public ResponseEntity<?> addEmployee(@Valid @RequestBody EmployeeRequestDto employee)
      throws ParseException {

    var response = employeeService.saveEmployee(employee);
    return ResponseEntity.ok(
        DataResponseDto.success(HttpStatus.OK.value(), MessageConst.SUCCESS, response));
  }

  @Hidden
  @Operation(summary = "Add list employee", security = {@SecurityRequirement(name = "bearer-key")})
  @PostMapping(value = {EndpointConst.Employee.LIST})
  public ResponseEntity<?> addEmployees(@Valid @RequestBody List<EmployeeEntity> employees) {
    var response = employeeService.saveEmployees(employees);
    return ResponseEntity.ok(
        DataResponseDto.success(HttpStatus.OK.value(), MessageConst.SUCCESS, response));

  }

  @Operation(summary = "Update employee by id", security = {
      @SecurityRequirement(name = "bearer-key")})
  @PutMapping(EndpointConst.Employee.UPDATE_BY_ID)
  public ResponseEntity<?> updateEmployee(@PathVariable UUID id,
      @RequestBody EmployeeRequestDto employee) {
    var response = employeeService.updateEmployee(id, employee);
    return ResponseEntity.ok(
        DataResponseDto.success(HttpStatus.OK.value(), MessageConst.SUCCESS, response));
  }

  @Operation(summary = "Delete employee by id", security = {
      @SecurityRequirement(name = "bearer-key")})
  @DeleteMapping(EndpointConst.Employee.DELETE_BY_ID)
  public ResponseEntity<?> deleteEmployee(@PathVariable UUID id) {
    try {
      employeeService.deleteEmployee(id);
      return ResponseEntity.ok(
          DataResponseDto.success(HttpStatus.OK.value(), MessageConst.SUCCESS, "success"));
    } catch (EmployeeNotFoundException ex) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
          DataResponseDto.error(HttpStatus.NOT_FOUND.value(), MessageConst.Employee.NOT_EXIST,
              ex.getMessage()));
    }
  }

  @Operation(summary = "Export employees data to excel", security = {
      @SecurityRequirement(name = "bearer-key")})
  @GetMapping(EndpointConst.Employee.EXPORT_EXCEL)
  public ResponseEntity<?> exportToExcel(HttpServletResponse response) throws IOException {

    DateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy_HH:mm:ss");
    String currentDateTime = dateFormatter.format(new Date());

    String headerKey = "Content-Disposition";
    String headerValue = "attachment; filename=EmployeeList__" + currentDateTime + ".xlsx";

    response.setHeader(headerKey, headerValue);
    response.setContentType("application/octet-stream");

    employeeExportToExcel.export(response);
    return ResponseEntity.ok(response);

  }
}