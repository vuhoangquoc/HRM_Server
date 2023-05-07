package com.intern.hrmanagementapi.model;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class ContractDto {

  private BigDecimal basicSalary;

  private String signDate;

  private String expireDate;

  private String workingTime;
}
