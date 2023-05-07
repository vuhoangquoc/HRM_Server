package com.intern.hrmanagementapi.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class SalaryDto {

  @NotNull(message = "Salary can't null")
  private BigDecimal salary;
  private BigDecimal bonus;
  private BigDecimal allowance;
  
  @NotEmpty(message = "Start Date can't empty")
  @NotNull(message = "Start Date can't null")
  private String startDate;

  @NotEmpty(message = "End Date can't empty")
  @NotNull(message = "End Date can't null")
  private String endDate;
}
