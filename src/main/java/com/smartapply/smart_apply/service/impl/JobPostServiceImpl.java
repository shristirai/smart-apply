package com.smartapply.smart_apply.service.impl;

import com.smartapply.smart_apply.dto.request.JobPostRequest;
import com.smartapply.smart_apply.dto.response.JobPostResponse;
import com.smartapply.smart_apply.model.JobPost;
import com.smartapply.smart_apply.model.User;
import com.smartapply.smart_apply.repository.JobPostRepository;
import com.smartapply.smart_apply.repository.UserRepository;
import com.smartapply.smart_apply.service.JobPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobPostServiceImpl implements JobPostService {

    private final JobPostRepository jobPostRepository;
    private final UserRepository userRepository;

    @Override
    public JobPostResponse createJob(JobPostRequest request,
                                     String recruiterEmail) {
        // verify user is a recruiter
        User recruiter = userRepository.findByEmail(recruiterEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!recruiter.getRole().equals("RECRUITER")) {
            throw new RuntimeException(
                    "Only recruiters can post jobs");
        }

        JobPost job = new JobPost();
        job.setRecruiterId(recruiter.getId());
        job.setTitle(request.getTitle());
        job.setCompany(request.getCompany());
        job.setLocation(request.getLocation());
        job.setDescription(request.getDescription());
        job.setRequiredSkills(request.getRequiredSkills());
        jobPostRepository.save(job);

        return toResponse(job);
    }

    @Override
    public List<JobPostResponse> getMyJobs(String recruiterEmail) {
        User recruiter = userRepository.findByEmail(recruiterEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return jobPostRepository
                .findByRecruiterId(recruiter.getId())
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<JobPostResponse> getAllJobs() {
        return jobPostRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private JobPostResponse toResponse(JobPost job) {
        List<String> skills = Arrays.stream(
                        job.getRequiredSkills().split(","))
                .map(String::trim)
                .collect(Collectors.toList());

        return new JobPostResponse(
                job.getId(),
                job.getTitle(),
                job.getCompany(),
                job.getLocation(),
                job.getDescription(),
                skills,
                job.getPostedAt()
        );
    }
}