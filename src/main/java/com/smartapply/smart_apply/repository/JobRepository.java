package com.smartapply.smart_apply.repository;

import com.smartapply.smart_apply.model.Job;
import com.smartapply.smart_apply.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

    List<Job> findByRecruiter(User recruiter);

    List<Job> findByLocation(String location);

    List<Job> findByCompany(String company);

    List<Job> findByTitleContainingIgnoreCase(String title);

    @Query("""
                SELECT DISTINCT j
                FROM Job j
                JOIN j.requiredSkills skill
                WHERE skill IN :skills
                """)
    List<Job> findJobsBySkills(@Param("skills") List<String> skills);
}
