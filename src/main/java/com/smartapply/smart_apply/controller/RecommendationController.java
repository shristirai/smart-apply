package com.smartapply.smart_apply.controller;

import com.smartapply.smart_apply.dto.response.RecommendationResponseDTO;
import com.smartapply.smart_apply.service.RecommendationService;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recommendations")
@RequiredArgsConstructor
public class RecommendationController {

    private final RecommendationService recommendationService;

    @GetMapping
    @PreAuthorize("hasRole('SEEKER')")
    public List<RecommendationResponseDTO> getRecommendations(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "5") @Min(1) int size) {

        return recommendationService.generateRecommendations(
                userDetails.getUsername(),
                page,
                size
        );
    }
}
