package com.smartapply.smart_apply.controller;

import com.smartapply.smart_apply.dto.gemini.request.CareerAdviceRequestDTO;
import com.smartapply.smart_apply.dto.gemini.response.GeminiResponseDTO;
import com.smartapply.smart_apply.service.ai.GeminiService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/gemini")
@RequiredArgsConstructor
public class GeminiController {

    private final GeminiService geminiService;

    @PostMapping("/career-advice")
    @PreAuthorize("hasRole('SEEKER')")
    public GeminiResponseDTO generateCareerAdvice(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody CareerAdviceRequestDTO request) {

        return geminiService.generateCareerAdvice(
                userDetails.getUsername(),
                request.getMatchedSkills(),
                request.getMissingSkills()
        );
    }
}