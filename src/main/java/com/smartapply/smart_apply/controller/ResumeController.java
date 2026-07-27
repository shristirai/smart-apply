package com.smartapply.smart_apply.controller;

import com.smartapply.smart_apply.dto.response.ResumeResponse;
import com.smartapply.smart_apply.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/resume")
@RequiredArgsConstructor
public class ResumeController {

    private final ResumeService resumeService;

    @PostMapping("/upload")
    @PreAuthorize("hasRole('SEEKER')")
    public ResponseEntity<ResumeResponse> uploadResume(
            @RequestParam("file") MultipartFile file,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(
                resumeService.uploadResume(file, userDetails.getUsername()));
    }

    @GetMapping("/analysis")
    @PreAuthorize("hasRole('SEEKER')")
    public ResponseEntity<ResumeResponse> getAnalysis(
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(
                resumeService.getResumeAnalysis(userDetails.getUsername()));
    }
}