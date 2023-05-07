package com.intern.hrmanagementapi.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentRequestDto {

  @NotEmpty(message = "Department name can't empty")
  @NotNull(message = "Department name can't null")
  private String name;
  
  private String description;
}
