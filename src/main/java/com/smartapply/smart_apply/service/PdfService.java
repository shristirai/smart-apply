package com.smartapply.smart_apply.service;

import org.springframework.web.multipart.MultipartFile;

public interface PdfService {
    String extractText(MultipartFile file);
}