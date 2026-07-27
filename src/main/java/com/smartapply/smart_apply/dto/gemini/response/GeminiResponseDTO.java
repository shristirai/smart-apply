package com.smartapply.smart_apply.dto.gemini.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class GeminiResponseDTO {

    private String careerSummary;

    private List<String> learningRoadmap;

    private List<String> interviewPreparation;

    private List<String> resumeImprovements;

}