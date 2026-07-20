package com.smartapply.smart_apply.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class RecommendationResponseDTO {

    private Long recommendationId;

    private String jobTitle;

    private String company;

    private Double matchPercentage;

    private LocalDateTime recommendedAt;
}
