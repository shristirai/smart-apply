package com.smartapply.smart_apply.controller;

import com.smartapply.smart_apply.dto.request.JobRequestDTO;
import com.smartapply.smart_apply.dto.response.JobResponseDTO;
import com.smartapply.smart_apply.service.JobService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;

    @PostMapping
    @PreAuthorize("hasRole('RECRUITER')")
    public JobResponseDTO createJob(
            @Valid @RequestBody JobRequestDTO jobRequestDTO,
            @AuthenticationPrincipal UserDetails userDetails) {

        return jobService.createJob(
                jobRequestDTO,
                userDetails.getUsername()
        );
    }

    @GetMapping("/{id}")
    public JobResponseDTO getJobById(@PathVariable Long id) {

        return jobService.getJobById(id);
    }

    @GetMapping
    public List<JobResponseDTO> getAllJobs() {

        return jobService.getAllJobs();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('RECRUITER')")
    public JobResponseDTO updateJob(
            @PathVariable Long id,
            @Valid @RequestBody JobRequestDTO jobRequestDTO,
            @AuthenticationPrincipal UserDetails userDetails) {

        return jobService.updateJob(
                id,
                jobRequestDTO,
                userDetails.getUsername()
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('RECRUITER')")
    public void deleteJob(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails) {

        jobService.deleteJob(
                id,
                userDetails.getUsername()
        );
    }

    @GetMapping("/search/title")
    public List<JobResponseDTO> searchByTitle(
            @RequestParam String title) {

        return jobService.searchJobsByTitle(title);
    }

    @GetMapping("/search/company")
    public List<JobResponseDTO> searchByCompany(
            @RequestParam String company) {

        return jobService.searchJobsByCompany(company);
    }

    @GetMapping("/search/location")
    public List<JobResponseDTO> searchByLocation(
            @RequestParam String location) {

        return jobService.searchJobsByLocation(location);
    }
}