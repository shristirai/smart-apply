package com.smartapply.smart_apply.service.impl;

import com.smartapply.smart_apply.dto.request.RecommendationRequestDTO;
import com.smartapply.smart_apply.dto.response.MatchResultDTO;
import com.smartapply.smart_apply.dto.response.RecommendationResponseDTO;
import com.smartapply.smart_apply.model.Job;
import com.smartapply.smart_apply.model.Recommendation;
import com.smartapply.smart_apply.repository.JobRepository;
import com.smartapply.smart_apply.repository.RecommendationRepository;
import com.smartapply.smart_apply.service.MatchingService;
import com.smartapply.smart_apply.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommendationServiceImpl implements RecommendationService {

    private final JobRepository jobRepository;
    private final RecommendationRepository recommendationRepository;
    private final MatchingService matchingService;

    @Override
    public List<RecommendationResponseDTO> generateRecommendations(
            RecommendationRequestDTO request) {

        List<JobRecommendation> recommendationList = new ArrayList<>();

        List<Job> jobs = jobRepository.findAll();

        for (Job job : jobs) {

            MatchResultDTO matchResult =
                    matchingService.calculateMatch(
                            request.getResumeSkills(),
                            job.getRequiredSkills());

            recommendationList.add(
                    new JobRecommendation(job, matchResult)
            );
        }

        recommendationList.sort(
                Comparator.comparing(
                                (JobRecommendation recommendation) ->
                                        recommendation.matchResult()
                                                .getMatchPercentage())
                        .reversed());

        if (recommendationList.size() > 5) {
            recommendationList =
                    recommendationList.subList(0, 5);
        }

        List<RecommendationResponseDTO> response =
                new ArrayList<>();

        for (JobRecommendation recommendationData : recommendationList) {

            Recommendation recommendation = new Recommendation();

            recommendation.setJob(recommendationData.job());
            recommendation.setMatchPercentage(
                    recommendationData.matchResult()
                            .getMatchPercentage());
            recommendation.setRecommendedAt(LocalDateTime.now());

            Recommendation savedRecommendation =
                    recommendationRepository.save(recommendation);

            RecommendationResponseDTO dto = getRecommendationResponseDTO(recommendationData, savedRecommendation);

            response.add(dto);
        }

        return response;
    }

    private static RecommendationResponseDTO getRecommendationResponseDTO(JobRecommendation recommendationData, Recommendation savedRecommendation) {
        RecommendationResponseDTO dto =
                new RecommendationResponseDTO();

        dto.setRecommendationId(savedRecommendation.getId());
        dto.setJobId(recommendationData.job().getId());
        dto.setJobTitle(recommendationData.job().getTitle());
        dto.setCompany(recommendationData.job().getCompany());

        dto.setMatchPercentage(
                recommendationData.matchResult()
                        .getMatchPercentage());

        dto.setMatchedSkills(
                recommendationData.matchResult()
                        .getMatchedSkills());

        dto.setMissingSkills(
                recommendationData.matchResult()
                        .getMissingSkills());

        dto.setRecommendedAt(
                savedRecommendation.getRecommendedAt());
        return dto;
    }

    private record JobRecommendation(Job job, MatchResultDTO matchResult) {

    }
}