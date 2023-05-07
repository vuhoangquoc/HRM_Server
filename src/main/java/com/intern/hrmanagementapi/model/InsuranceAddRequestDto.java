package com.intern.hrmanagementapi.model;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.Getter;

@Getter
public class InsuranceAddRequestDto extends InsuranceDto {

  @NotNull(message = "Employee ID can't null")
  private UUID employeeId;

}
