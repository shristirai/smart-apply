package com.smartapply.smart_apply.repository;

import com.smartapply.smart_apply.model.Job;
import com.smartapply.smart_apply.model.Recommendation;
import com.smartapply.smart_apply.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecommendationRepository extends JpaRepository<Recommendation, Long> {

    Optional<Recommendation> findByUserAndJob(User user, Job job);
}
