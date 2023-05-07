package com.intern.hrmanagementapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class InsuranceDto {

  private String number;
  private String issuedDate;
  private String issuedPlace;
}
