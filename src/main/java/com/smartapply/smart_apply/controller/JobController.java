package com.smartapply.smart_apply.controller;

import com.smartapply.smart_apply.dto.request.JobRequestDTO;
import com.smartapply.smart_apply.dto.response.JobResponseDTO;
import com.smartapply.smart_apply.service.JobService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobs")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;

    @PostMapping
    public JobResponseDTO createJob(
            @Valid @RequestBody JobRequestDTO jobRequestDTO) {

        return jobService.createJob(jobRequestDTO);
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
    public JobResponseDTO updateJob(
            @PathVariable Long id,
            @Valid @RequestBody JobRequestDTO jobRequestDTO) {

        return jobService.updateJob(id, jobRequestDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteJob(@PathVariable Long id) {

        jobService.deleteJob(id);
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