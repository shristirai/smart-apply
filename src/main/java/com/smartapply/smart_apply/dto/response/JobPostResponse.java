package com.smartapply.smart_apply.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class JobPostResponse {
    private Long id;
    private String title;
    private String company;
    private String location;
    private String description;
    private List<String> requiredSkills;
    private LocalDateTime postedAt;
}