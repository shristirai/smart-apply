package com.smartapply.smart_apply.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SkillGapResponseDTO {

    private List<String> matchedSkills;

    private List<String> missingSkills;

    private Double matchPercentage;
}
