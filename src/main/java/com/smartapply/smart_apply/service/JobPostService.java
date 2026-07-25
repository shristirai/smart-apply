package com.smartapply.smart_apply.service;

import com.smartapply.smart_apply.dto.request.JobPostRequest;
import com.smartapply.smart_apply.dto.response.JobPostResponse;
import java.util.List;

public interface JobPostService {
    JobPostResponse createJob(JobPostRequest request, String recruiterEmail);
    List<JobPostResponse> getMyJobs(String recruiterEmail);
    List<JobPostResponse> getAllJobs();
}