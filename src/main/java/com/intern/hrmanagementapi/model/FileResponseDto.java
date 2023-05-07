package com.intern.hrmanagementapi.model;

import java.util.Date;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class FileResponseDto {

  private UUID id;
  private String name;
  private String downloadUrl;
  private String url;
  private String type;
  private long size;
  private Date createdAt;
}
