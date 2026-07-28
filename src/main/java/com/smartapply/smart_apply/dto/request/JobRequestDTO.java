package com.smartapply.smart_apply.dto.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class JobRequestDTO {

    @NotBlank(message = "Job title is required.")
    @Size(max = 100, message = "Job title cannot exceed 100 characters.")
    private String title;

    @NotBlank(message = "Company name is required.")
    @Size(max = 100, message = "Company name cannot exceed 100 characters.")
    private String company;

    @NotBlank(message = "Location is required.")
    @Size(max = 100, message = "Location cannot exceed 100 characters.")
    private String location;

    @NotBlank(message = "Job description is required.")
    @Size(max = 5000, message = "Job description cannot exceed 5000 characters.")
    private String description;

    @NotEmpty(message = "At least one required skill must be provided.")
    private List<
            @NotBlank(message = "Skill cannot be blank.")
                    String
            > requiredSkills;

    @NotNull(message = "Experience is required.")
    @Min(value = 0, message = "Experience cannot be negative.")
    @Max(value = 30, message = "Experience cannot exceed 30 years.")
    private Integer experience;

    @NotNull(message = "Salary is required.")
    @Positive(message = "Salary must be greater than zero.")
    private Double salary;
}