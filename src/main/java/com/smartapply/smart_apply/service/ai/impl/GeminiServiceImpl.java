package com.smartapply.smart_apply.service.ai.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartapply.smart_apply.config.GeminiConfig;
import com.smartapply.smart_apply.dto.gemini.request.ContentDTO;
import com.smartapply.smart_apply.dto.gemini.request.GeminiRequestDTO;
import com.smartapply.smart_apply.dto.gemini.request.PartDTO;
import com.smartapply.smart_apply.dto.gemini.response.GeminiResponseDTO;
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

    @Override
    public GeminiResponseDTO generateCareerAdvice(
            List<String> matchedSkills,
            List<String> missingSkills) {

        String prompt = buildPrompt(matchedSkills, missingSkills);

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

        String response = webClient.post()
                .uri(url)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();

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
            throw new RuntimeException("Failed to parse Gemini response", e);
        }
    }

    private String buildPrompt(
            List<String> matchedSkills,
            List<String> missingSkills) {

        return """
            You are an expert Career Advisor and Resume Reviewer.

            Candidate Matched Skills:
            %s

            Candidate Missing Skills:
            %s

            Generate personalized career advice.

            IMPORTANT RULES:
            1. Return ONLY valid JSON.
            2. Do NOT use markdown.
            3. Do NOT wrap the JSON in ```json.
            4. Do NOT add explanations.
            5. Do NOT return null for any field.
            6. Every array must contain at least 3 items.
            7. resumeImprovements MUST contain exactly 3 practical resume suggestions.

            Return this exact JSON structure:

            {
              "careerSummary": "",
              "learningRoadmap": [],
              "interviewPreparation": [],
              "resumeImprovements": []
            }
            """.formatted(
                String.join(", ", matchedSkills),
                String.join(", ", missingSkills)
        );
    }
}