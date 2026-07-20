package com.smartapply.smart_apply.service;

import com.smartapply.smart_apply.dto.request.RecommendationRequestDTO;
import com.smartapply.smart_apply.dto.response.RecommendationResponseDTO;

import java.util.List;

public interface RecommendationService {

    List<RecommendationResponseDTO> generateRecommendations(
            RecommendationRequestDTO request);

}
