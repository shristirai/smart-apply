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
public class JobResponseDTO {

    private Long id;

    private String title;

    private String company;

    private String location;

    private String description;

    private List<String> requiredSkills;

    private Integer experience;

    private Double salary;
}