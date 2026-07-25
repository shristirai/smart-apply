package com.smartapply.smart_apply.repository;

import com.smartapply.smart_apply.model.JobPost;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface JobPostRepository extends JpaRepository<JobPost, Long> {

    // get all jobs posted by a specific recruiter
    List<JobPost> findByRecruiterId(Long recruiterId);

    // get all jobs — used by Prachi for recommendations
    List<JobPost> findAll();
}