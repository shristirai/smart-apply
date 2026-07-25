package com.smartapply.smart_apply.repository;

import com.smartapply.smart_apply.model.UserSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UserSkillRepository extends JpaRepository<UserSkill, Long> {

    // Prachi will use this to fetch skills for matching
    List<UserSkill> findByUserId(Long userId);

    // delete old skills before saving new ones on re-upload
    void deleteByUserId(Long userId);
}