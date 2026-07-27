package com.smartapply.smart_apply.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MatchResultDTO {

    private Double matchPercentage;

    private List<String> matchedSkills;

    private List<String> missingSkills;
}
