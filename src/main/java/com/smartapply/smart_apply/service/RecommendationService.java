package com.smartapply.smart_apply.service;

import com.smartapply.smart_apply.dto.response.RecommendationResponseDTO;

import java.util.List;

public interface RecommendationService {

    public List<RecommendationResponseDTO> generateRecommendations(
            String email,
            int page,
            int size);

}
