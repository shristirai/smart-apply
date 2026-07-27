package com.smartapply.smart_apply.service.impl;

import com.smartapply.smart_apply.model.UserSkill;
import com.smartapply.smart_apply.repository.UserSkillRepository;
import com.smartapply.smart_apply.service.SkillExtractionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SkillExtractionServiceImpl implements SkillExtractionService {

    private final UserSkillRepository userSkillRepository;

    private static final List<String> STOP_SECTIONS = List.of(
            "WORK EXPERIENCE", "EXPERIENCE", "EMPLOYMENT",
            "EDUCATION", "PROJECTS", "CERTIFICATIONS",
            "CERTIFICATION", "AWARDS", "INTERNSHIP",
            "SUMMARY", "OBJECTIVE",
            "ACHIEVEMENTS", "PUBLICATIONS", "REFERENCES"
    );

    private static final List<String> SKILL_HEADINGS = List.of(
            "SKILLS", "TECHNICAL SKILLS", "PROFESSIONAL SKILLS",
            "CORE COMPETENCIES", "COMPETENCIES", "KEY SKILLS",
            "EXPERTISE", "TECHNOLOGIES", "TOOLS"
    );

    @Override
    @Transactional
    public List<String> extractAndSaveSkills(String resumeText, Long userId) {

        List<String> skills = extractSkillsFromText(resumeText);

        // delete old skills
        userSkillRepository.deleteByUserId(userId);

        // save each skill
        skills.forEach(skill -> {
            UserSkill userSkill = new UserSkill();
            userSkill.setUserId(userId);
            userSkill.setSkill(skill);
            userSkillRepository.save(userSkill);
        });

        return skills;
    }

    private List<String> extractSkillsFromText(String resumeText) {
        String[] lines = resumeText.split("\\n");

        boolean insideSkillsSection = false;
        List<String> skills = new ArrayList<>();

        for (String line : lines) {
            String trimmed = line.trim();
            String upper = trimmed.toUpperCase();

            // check if this line is a SKILLS section heading
            if (isSkillHeading(upper)) {
                insideSkillsSection = true;
                continue; // skip the heading line itself
            }

            // check if we hit another section — stop collecting
            if (insideSkillsSection && isStopSection(upper)) {
                break;
            }

            // collect lines inside skills section
            if (insideSkillsSection && !trimmed.isEmpty()) {
                // remove bullet points and leading symbols
                String cleaned = trimmed
                        .replaceAll("^[•\\-–*►▪◦●]\\s*", "")
                        .trim();

                if (cleaned.isEmpty()) continue;

                // if line has colon — it's "Category: skill1, skill2"
                // extract just the skills part after colon
                if (cleaned.contains(":")) {
                    String afterColon = cleaned
                            .substring(cleaned.indexOf(":") + 1)
                            .trim();
                    // split by comma
                    String[] parts = afterColon.split(",");
                    for (String part : parts) {
                        String s = part.trim();
                        if (!s.isEmpty() && s.length() > 1) {
                            skills.add(s);
                        }
                    }
                } else {
                    // split by comma if multiple on one line
                    String[] parts = cleaned.split(",");
                    for (String part : parts) {
                        String s = part.trim();
                        if (!s.isEmpty() && s.length() > 1) {
                            skills.add(s);
                        }
                    }
                }
            }
        }

        // remove duplicates
        return skills.stream()
                .distinct()
                .collect(Collectors.toList());
    }

    private boolean isSkillHeading(String line) {
        return SKILL_HEADINGS.stream()
                .anyMatch(heading -> line.equals(heading)
                        || line.startsWith(heading));
    }

    private boolean isStopSection(String line) {
        return STOP_SECTIONS.stream()
                .anyMatch(stop -> line.equals(stop)
                        || line.startsWith(stop));
    }
}