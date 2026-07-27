package com.smartapply.smart_apply.dto.gemini.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CareerAdviceRequestDTO {

    @NotEmpty(message = "Matched skills cannot be empty")
    private List<String> matchedSkills;

    @NotEmpty(message = "Missing skills cannot be empty")
    private List<String> missingSkills;

}