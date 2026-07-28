package com.smartapply.smart_apply.service.impl;

import com.smartapply.smart_apply.dto.response.MatchResultDTO;
import com.smartapply.smart_apply.dto.response.RecommendationResponseDTO;
import com.smartapply.smart_apply.exception.SmartApplyErrorMessage;
import com.smartapply.smart_apply.exception.SmartApplyException;
import com.smartapply.smart_apply.model.Job;
import com.smartapply.smart_apply.model.Recommendation;
import com.smartapply.smart_apply.model.User;
import com.smartapply.smart_apply.model.UserSkill;
import com.smartapply.smart_apply.repository.JobRepository;
import com.smartapply.smart_apply.repository.RecommendationRepository;
import com.smartapply.smart_apply.repository.UserRepository;
import com.smartapply.smart_apply.repository.UserSkillRepository;
import com.smartapply.smart_apply.service.MatchingService;
import com.smartapply.smart_apply.service.RecommendationService;
import jakarta.transaction.Transactional;
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
    private final UserRepository userRepository;
    private final UserSkillRepository userSkillRepository;


    @Override
    @Transactional
    public List<RecommendationResponseDTO> generateRecommendations(
            String email,
            int page,
            int size) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new SmartApplyException(
                                SmartApplyErrorMessage.USER_NOT_FOUND
                        )
                );


        List<String> resumeSkills =
                userSkillRepository.findByUserId(user.getId())
                        .stream()
                        .map(UserSkill::getSkill)
                        .toList();


        List<JobRecommendation> recommendationList =
                new ArrayList<>();


        List<Job> jobs =
                jobRepository.findJobsBySkills(resumeSkills);


        for (Job job : jobs) {

            MatchResultDTO matchResult =
                    matchingService.calculateMatch(
                            resumeSkills,
                            job.getRequiredSkills()
                    );


            recommendationList.add(
                    new JobRecommendation(job, matchResult)
            );
        }

        recommendationList.sort(
                Comparator.comparing(
                                (JobRecommendation recommendation) ->
                                        recommendation.matchResult()
                                                .getMatchPercentage())
                        .reversed()
        );


        int start = page * size;

        if (start >= recommendationList.size()) {
            return List.of();
        }

        int end = Math.min(start + size, recommendationList.size());

        recommendationList =
                recommendationList.subList(start, end);


        List<Recommendation> recommendationsToSave = new ArrayList<>();

        for (JobRecommendation recommendationData : recommendationList) {

            Recommendation recommendation =
                    recommendationRepository
                            .findByUserAndJob(
                                    user,
                                    recommendationData.job()
                            )
                            .orElse(new Recommendation());

            recommendation.setUser(user);

            recommendation.setJob(
                    recommendationData.job()
            );

            recommendation.setMatchPercentage(
                    recommendationData.matchResult()
                            .getMatchPercentage()
            );

            recommendation.setRecommendedAt(
                    LocalDateTime.now()
            );

            recommendationsToSave.add(recommendation);
        }

        List<Recommendation> savedRecommendations =
                recommendationRepository.saveAll(recommendationsToSave);

        List<RecommendationResponseDTO> response =
                new ArrayList<>();

        for (int i = 0; i < savedRecommendations.size(); i++) {

            RecommendationResponseDTO dto =
                    getRecommendationResponseDTO(
                            recommendationList.get(i),
                            savedRecommendations.get(i)
                    );

            response.add(dto);
        }

        return response;
    }


    private static RecommendationResponseDTO getRecommendationResponseDTO(
            JobRecommendation recommendationData,
            Recommendation savedRecommendation) {

        RecommendationResponseDTO dto =
                new RecommendationResponseDTO();
        dto.setRecommendationId(
                savedRecommendation.getId()
        );
        dto.setJobId(
                recommendationData.job().getId()
        );
        dto.setJobTitle(
                recommendationData.job().getTitle()
        );
        dto.setCompany(
                recommendationData.job().getCompany()
        );
        dto.setMatchPercentage(
                recommendationData.matchResult()
                        .getMatchPercentage()
        );
        dto.setMatchedSkills(
                recommendationData.matchResult()
                        .getMatchedSkills()
        );
        dto.setMissingSkills(
                recommendationData.matchResult()
                        .getMissingSkills()
        );
        dto.setRecommendedAt(
                savedRecommendation.getRecommendedAt()
        );


        return dto;
    }


    private record JobRecommendation(
            Job job,
            MatchResultDTO matchResult
    ) {

    }
}