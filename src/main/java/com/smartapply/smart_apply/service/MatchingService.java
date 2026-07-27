package com.smartapply.smart_apply.service;

import com.smartapply.smart_apply.dto.response.MatchResultDTO;

import java.util.List;

public interface MatchingService {

    MatchResultDTO calculateMatch(List<String> resumeSkills,
                                            List<String> jobSkills);

}
