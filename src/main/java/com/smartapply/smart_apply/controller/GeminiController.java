package com.smartapply.smart_apply.controller;

import com.smartapply.smart_apply.dto.gemini.request.CareerAdviceRequestDTO;
import com.smartapply.smart_apply.dto.gemini.response.GeminiResponseDTO;
import com.smartapply.smart_apply.service.ai.GeminiService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/gemini")
@RequiredArgsConstructor
public class GeminiController {

    private final GeminiService geminiService;

    @PostMapping("/career-advice")
    public GeminiResponseDTO generateCareerAdvice(
            @Valid @RequestBody CareerAdviceRequestDTO request) {

        return geminiService.generateCareerAdvice(
                request.getMatchedSkills(),
                request.getMissingSkills()
        );
    }
}