package com.smartapply.smart_apply.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.List;

@Getter
@AllArgsConstructor
public class ResumeResponse {
    private Long resumeId;
    private String filePath;
    private List<String> extractedSkills;
}