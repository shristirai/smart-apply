package com.smartapply.smart_apply.service.impl;

import com.smartapply.smart_apply.dto.response.MatchResultDTO;
import com.smartapply.smart_apply.service.MatchingService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class MatchServiceImpl implements MatchingService {

    @Override
    public MatchResultDTO calculateMatch(List<String> resumeSkills,
                                         List<String> jobSkills) {

        if (resumeSkills == null || resumeSkills.isEmpty()
                || jobSkills == null || jobSkills.isEmpty()) {

            return new MatchResultDTO(
                    0.0,
                    new ArrayList<>(),
                    new ArrayList<>()
            );
        }

        Set<String> normalizedResumeSkills = new HashSet<>();

        for (String skill : resumeSkills) {
            normalizedResumeSkills.add(skill.trim().toLowerCase());
        }

        List<String> matchedSkills = new ArrayList<>();
        List<String> missingSkills = new ArrayList<>();

        for (String skill : jobSkills) {

            String normalizedSkill = skill.trim().toLowerCase();

            if (normalizedResumeSkills.contains(normalizedSkill)) {
                matchedSkills.add(skill);
            } else {
                missingSkills.add(skill);
            }
        }

        double percentage =
                ((double) matchedSkills.size() / jobSkills.size()) * 100;

        percentage = Math.round(percentage * 100.0) / 100.0;

        return new MatchResultDTO(
                percentage,
                matchedSkills,
                missingSkills
        );
    }
}