package com.intern.hrmanagementapi.model;

import jakarta.validation.constraints.Email;
import java.util.UUID;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class EmployeeRequestDto {


  @NotNull(message = "FirstName is required.")
  @NotEmpty(message = "FirstName is not empty or blank.")
  private String firstName;

  @NotNull(message = "LastName is required.")
  @NotEmpty(message = "LastName is not empty or blank.")
  private String lastName;

  @NotNull(message = "Gender is required.")
  private int gender;

  @NotNull(message = "Address is required.")
  @NotEmpty(message = "Address is not empty or blank.")
  private String address;

  @NotEmpty(message = "Email can't empty")
  @Email(message = "Email is invalid")
  private String email;

  @NotNull(message = "Date of birth is required.")
  private String dob;

  @NotNull(message = "PositionId is required.")
  private UUID positionId;

  @NotNull(message = "DepartmentId is required.")
  private UUID departmentId;
}
