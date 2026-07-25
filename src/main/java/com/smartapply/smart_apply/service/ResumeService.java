package com.smartapply.smart_apply.service;

import com.smartapply.smart_apply.dto.response.ResumeResponse;
import org.springframework.web.multipart.MultipartFile;

public interface ResumeService {
    ResumeResponse uploadResume(MultipartFile file, String email);
    ResumeResponse getResumeAnalysis(String email);
}