package com.smartapply.smart_apply.dto.gemini.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CareerAdviceRequestDTO {

    private List<String> matchedSkills;

    private List<String> missingSkills;

}