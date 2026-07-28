package com.smartapply.smart_apply.service.ai.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartapply.smart_apply.config.GeminiConfig;
import com.smartapply.smart_apply.dto.gemini.request.ContentDTO;
import com.smartapply.smart_apply.dto.gemini.request.GeminiRequestDTO;
import com.smartapply.smart_apply.dto.gemini.request.PartDTO;
import com.smartapply.smart_apply.dto.gemini.response.GeminiResponseDTO;
import com.smartapply.smart_apply.exception.SmartApplyErrorMessage;
import com.smartapply.smart_apply.exception.SmartApplyException;
import com.smartapply.smart_apply.model.Resume;
import com.smartapply.smart_apply.model.User;
import com.smartapply.smart_apply.repository.ResumeRepository;
import com.smartapply.smart_apply.repository.UserRepository;
import com.smartapply.smart_apply.service.ai.GeminiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GeminiServiceImpl implements GeminiService {

    private final WebClient webClient;
    private final GeminiConfig geminiConfig;
    private final ObjectMapper objectMapper;
    private final ResumeRepository resumeRepository;
    private final UserRepository userRepository;

    @Override
    public GeminiResponseDTO generateCareerAdvice(
            String email,
            List<String> matchedSkills,
            List<String> missingSkills) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new SmartApplyException(
                                SmartApplyErrorMessage.USER_NOT_FOUND
                        )
                );

        Resume resume = resumeRepository.findByUserId(user.getId())
                .orElseThrow(() ->
                        new SmartApplyException(
                                SmartApplyErrorMessage.RESUME_NOT_FOUND
                        )
                );

        String prompt = buildPrompt(
                resume.getExtractedText(),
                matchedSkills,
                missingSkills
        );

        String url =
                "https://generativelanguage.googleapis.com/v1beta/models/"
                        + geminiConfig.getModel()
                        + ":generateContent?key="
                        + geminiConfig.getApiKey();

        GeminiRequestDTO requestBody = new GeminiRequestDTO(
                List.of(
                        new ContentDTO(
                                List.of(
                                        new PartDTO(prompt)
                                )
                        )
                )
        );

        String response;

        try {
            response = webClient.post()
                    .uri(url)
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (Exception e) {
            throw new SmartApplyException(
                    SmartApplyErrorMessage.GEMINI_API_ERROR
            );
        }

        try {

            JsonNode root = objectMapper.readTree(response);

            String aiText = root
                    .path("candidates")
                    .get(0)
                    .path("content")
                    .path("parts")
                    .get(0)
                    .path("text")
                    .asText();

            return objectMapper.readValue(
                    aiText,
                    GeminiResponseDTO.class
            );

        } catch (Exception e) {
            throw new SmartApplyException(
                    SmartApplyErrorMessage.GEMINI_RESPONSE_PARSE_ERROR
            );
        }
    }

    private String buildPrompt(
            String resumeText,
            List<String> matchedSkills,
            List<String> missingSkills) {

        return """
            You are an expert Career Advisor and Resume Reviewer.

            Candidate Resume:

            %s

            Candidate Matched Skills:
            %s

            Candidate Missing Skills:
            %s

            Analyze both the resume and the skill gap.

            IMPORTANT RULES:
            1. Return ONLY valid JSON.
            2. Do NOT use markdown.
            3. Do NOT wrap the JSON inside ```json.
            4. Do NOT add explanations.
            5. Do NOT return null.
            6. Every array must contain at least 3 items.
            7. Resume improvements MUST be based on the actual resume content.
            8. Learning roadmap should focus on the missing skills.
            9. Interview preparation should be personalized.

            Return this exact JSON:

            {
              "careerSummary": "",
              "learningRoadmap": [],
              "interviewPreparation": [],
              "resumeImprovements": []
            }
            """.formatted(
                resumeText,
                String.join(", ", matchedSkills),
                String.join(", ", missingSkills)
        );
    }
}