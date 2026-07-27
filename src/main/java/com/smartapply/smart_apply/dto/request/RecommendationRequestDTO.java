package com.smartapply.smart_apply.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class RecommendationRequestDTO {

    private List<String> resumeSkills;

}
