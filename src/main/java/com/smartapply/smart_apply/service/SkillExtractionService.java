package com.smartapply.smart_apply.service;

import java.util.List;

public interface SkillExtractionService {
    List<String> extractAndSaveSkills(String resumeText, Long userId);
}