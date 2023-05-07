package com.intern.hrmanagementapi.util;

import com.intern.hrmanagementapi.constant.EndpointConst;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public final class FileUtil {

  public static String getFileDownloadUri(String fileId) {
    return ServletUriComponentsBuilder.fromCurrentContextPath().path(EndpointConst.File.BASE_PATH)
        .path("/download/").path(fileId).toUriString();
  }
}
