package com.smartapply.smart_apply.service.ai;

import com.smartapply.smart_apply.dto.gemini.response.GeminiResponseDTO;

import java.util.List;

public interface GeminiService {

    GeminiResponseDTO generateCareerAdvice(
            String email,
            List<String> matchedSkills,
            List<String> missingSkills
    );
}