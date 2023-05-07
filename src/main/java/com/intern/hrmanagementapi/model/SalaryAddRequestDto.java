package com.intern.hrmanagementapi.model;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.Getter;

@Getter
public class SalaryAddRequestDto extends SalaryDto {

  @NotNull(message = "Employee ID can't null")
  private UUID employeeId;
}
