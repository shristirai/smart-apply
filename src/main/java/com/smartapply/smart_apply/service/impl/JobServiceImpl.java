package com.smartapply.smart_apply.service.impl;

import com.smartapply.smart_apply.dto.request.JobRequestDTO;
import com.smartapply.smart_apply.dto.response.JobResponseDTO;
import com.smartapply.smart_apply.exception.SmartApplyErrorMessage;
import com.smartapply.smart_apply.exception.SmartApplyException;
import com.smartapply.smart_apply.model.Job;
import com.smartapply.smart_apply.model.User;
import com.smartapply.smart_apply.repository.JobRepository;
import com.smartapply.smart_apply.repository.UserRepository;
import com.smartapply.smart_apply.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;
    private final UserRepository userRepository;

    @Override
    public JobResponseDTO createJob(
            JobRequestDTO jobRequestDTO,
            String email) {
        User recruiter = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new SmartApplyException(
                                SmartApplyErrorMessage.RECRUITER_NOT_FOUND
                        ));
        Job job = mapToEntity(jobRequestDTO);
        job.setRecruiter(recruiter);

        Job savedJob = jobRepository.save(job);

        return mapToResponse(savedJob);
    }

    @Override
    public JobResponseDTO getJobById(Long id) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() ->
                        new SmartApplyException(
                                SmartApplyErrorMessage.JOB_NOT_FOUND
                        ));

        return mapToResponse(job);
    }

    @Override
    public List<JobResponseDTO> getAllJobs() {
        List<Job> jobs = jobRepository.findAll();

        List<JobResponseDTO> response = new ArrayList<>();

        for (Job job : jobs) {
            response.add(mapToResponse(job));
        }

        return response;
    }

    @Override
    public List<JobResponseDTO> getMyJobs(String email) {
        User recruiter = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new SmartApplyException(
                                SmartApplyErrorMessage.RECRUITER_NOT_FOUND
                        ));

        List<Job> jobs = jobRepository.findByRecruiter(recruiter);
        List<JobResponseDTO> response = new ArrayList<>();
        for(Job job : jobs){

            response.add(mapToResponse(job));

        }
        return response;

    }

    @Override
    public JobResponseDTO updateJob(Long id, JobRequestDTO jobRequestDTO, String email) {

        Job job = jobRepository.findById(id)
                .orElseThrow(() ->
                        new SmartApplyException(
                                SmartApplyErrorMessage.JOB_NOT_FOUND
                        ));

        if (!job.getRecruiter().getEmail().equals(email)) {
            throw new SmartApplyException(
                    SmartApplyErrorMessage.JOB_UPDATE_NOT_ALLOWED
            );
        }

        job.setTitle(jobRequestDTO.getTitle());
        job.setCompany(jobRequestDTO.getCompany());
        job.setLocation(jobRequestDTO.getLocation());
        job.setDescription(jobRequestDTO.getDescription());
        job.setRequiredSkills(jobRequestDTO.getRequiredSkills());
        job.setExperience(jobRequestDTO.getExperience());
        job.setSalary(jobRequestDTO.getSalary());

        Job updatedJob = jobRepository.save(job);

        return mapToResponse(updatedJob);
    }

    @Override
    public void deleteJob(Long id, String email) {

        Job job = jobRepository.findById(id)
                .orElseThrow(() ->
                        new SmartApplyException(
                                SmartApplyErrorMessage.JOB_NOT_FOUND
                        ));

        if (!job.getRecruiter().getEmail().equals(email)) {
            throw new SmartApplyException(
                    SmartApplyErrorMessage.JOB_DELETE_NOT_ALLOWED
            );
        }

        jobRepository.delete(job);
    }

    @Override
    public List<JobResponseDTO> searchJobsByTitle(String title) {
        List<Job> jobs = jobRepository.findByTitleContainingIgnoreCase(title);

        List<JobResponseDTO> response = new ArrayList<>();

        for (Job job : jobs) {
            response.add(mapToResponse(job));
        }

        return response;
    }

    @Override
    public List<JobResponseDTO> searchJobsByCompany(String company) {
        List<Job> jobs = jobRepository.findByCompany(company);

        List<JobResponseDTO> response = new ArrayList<>();

        for (Job job : jobs) {
            response.add(mapToResponse(job));
        }

        return response;
    }

    @Override
    public List<JobResponseDTO> searchJobsByLocation(String location) {
        List<Job> jobs = jobRepository.findByLocation(location);

        List<JobResponseDTO> response = new ArrayList<>();

        for (Job job : jobs) {
            response.add(mapToResponse(job));
        }

        return response;
    }

    private Job mapToEntity(JobRequestDTO dto) {

        Job job = new Job();

        job.setTitle(dto.getTitle());
        job.setCompany(dto.getCompany());
        job.setLocation(dto.getLocation());
        job.setDescription(dto.getDescription());
        job.setRequiredSkills(dto.getRequiredSkills());
        job.setExperience(dto.getExperience());
        job.setSalary(dto.getSalary());

        return job;
    }

    private JobResponseDTO mapToResponse(Job job) {

        JobResponseDTO dto = new JobResponseDTO();

        dto.setId(job.getId());
        dto.setTitle(job.getTitle());
        dto.setCompany(job.getCompany());
        dto.setLocation(job.getLocation());
        dto.setDescription(job.getDescription());
        dto.setRequiredSkills(job.getRequiredSkills());
        dto.setExperience(job.getExperience());
        dto.setSalary(job.getSalary());

        return dto;
    }
}
