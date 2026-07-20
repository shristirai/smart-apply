package com.smartapply.smart_apply.service;

import java.util.List;

public interface MatchingService {

    Double calculateMatchPercentage(List<String> resumeSkills,
                                    List<String> jobSkills);

}
