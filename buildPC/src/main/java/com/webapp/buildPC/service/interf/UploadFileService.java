package com.webapp.buildPC.service.interf;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UploadFileService {

    String uploadImage(MultipartFile file) throws IOException;
}
