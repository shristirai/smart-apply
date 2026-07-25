package com.smartapply.smart_apply.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobPostRequest {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Company is required")
    private String company;

    private String location;

    private String description;

    // comma separated: "Java,Spring Boot,MySQL"
    @NotBlank(message = "Required skills are required")
    private String requiredSkills;
}