package com.smartapply.smart_apply.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RecommendationRequestDTO {
    private Long jobId;

    private String resume;
}
