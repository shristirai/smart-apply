package com.smartapply.smart_apply.service;

import com.smartapply.smart_apply.dto.request.JobRequestDTO;
import com.smartapply.smart_apply.dto.response.JobResponseDTO;

import java.util.List;

public interface JobService {
    JobResponseDTO createJob(
            JobRequestDTO jobRequestDTO,
            String email
    );

    JobResponseDTO getJobById(Long id);

    List<JobResponseDTO> getAllJobs();

    JobResponseDTO updateJob(Long id, JobRequestDTO jobRequestDTO, String email);

    void deleteJob(Long id, String email);

    List<JobResponseDTO> searchJobsByTitle(String title);

    List<JobResponseDTO> searchJobsByCompany(String company);

    List<JobResponseDTO> searchJobsByLocation(String location);
}
