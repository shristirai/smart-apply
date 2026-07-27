package com.smartapply.smart_apply.controller;

import com.smartapply.smart_apply.dto.request.RecommendationRequestDTO;
import com.smartapply.smart_apply.dto.response.RecommendationResponseDTO;
import com.smartapply.smart_apply.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/recommendations")
@RequiredArgsConstructor
public class RecommendationController {

    private final RecommendationService recommendationService;

    @PostMapping
    public List<RecommendationResponseDTO> generateRecommendations(
            @RequestBody RecommendationRequestDTO request) {

        return recommendationService.generateRecommendations(request);
    }
}
