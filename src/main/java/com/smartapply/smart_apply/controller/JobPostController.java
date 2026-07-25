package com.smartapply.smart_apply.controller;

import com.smartapply.smart_apply.dto.request.JobPostRequest;
import com.smartapply.smart_apply.dto.response.JobPostResponse;
import com.smartapply.smart_apply.service.JobPostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
public class JobPostController {

    private final JobPostService jobPostService;

    // RECRUITER posts a job
    @PostMapping("/post")
    public ResponseEntity<JobPostResponse> postJob(
            @Valid @RequestBody JobPostRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(
                jobPostService.createJob(request,
                        userDetails.getUsername()));
    }

    // RECRUITER sees their own jobs
    @GetMapping("/my-jobs")
    public ResponseEntity<List<JobPostResponse>> getMyJobs(
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(
                jobPostService.getMyJobs(userDetails.getUsername()));
    }

    // SEEKER / anyone sees all jobs
    @GetMapping("/all")
    public ResponseEntity<List<JobPostResponse>> getAllJobs() {
        return ResponseEntity.ok(jobPostService.getAllJobs());
    }
}