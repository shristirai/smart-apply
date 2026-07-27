package com.smartapply.smart_apply.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecommendationResponseDTO {

    private Long recommendationId;

    private String jobTitle;

    private Long jobId;

    private String company;

    private Double matchPercentage;

    private List<String> matchedSkills;

    private List<String> missingSkills;

    private LocalDateTime recommendedAt;
}
