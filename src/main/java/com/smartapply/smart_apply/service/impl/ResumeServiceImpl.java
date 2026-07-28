package com.smartapply.smart_apply.service.impl;

import com.smartapply.smart_apply.dto.response.ResumeResponse;
import com.smartapply.smart_apply.exception.SmartApplyErrorMessage;
import com.smartapply.smart_apply.exception.SmartApplyException;
import com.smartapply.smart_apply.model.Resume;
import com.smartapply.smart_apply.model.User;
import com.smartapply.smart_apply.model.UserSkill;
import com.smartapply.smart_apply.repository.ResumeRepository;
import com.smartapply.smart_apply.repository.UserRepository;
import com.smartapply.smart_apply.repository.UserSkillRepository;
import com.smartapply.smart_apply.service.PdfService;
import com.smartapply.smart_apply.service.ResumeService;
import com.smartapply.smart_apply.service.SkillExtractionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {

    private final PdfService pdfService;
    private final SkillExtractionService skillExtractionService;
    private final ResumeRepository resumeRepository;
    private final UserRepository userRepository;
    private final UserSkillRepository userSkillRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Override
    public ResumeResponse uploadResume(MultipartFile file, String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new SmartApplyException(
                                SmartApplyErrorMessage.USER_NOT_FOUND
                        )
                );

        if (file.isEmpty()) {
            throw new SmartApplyException(
                    SmartApplyErrorMessage.INVALID_RESUME_FILE
            );
        }

        String originalFileName = file.getOriginalFilename();

        if (originalFileName == null
                || !originalFileName.toLowerCase().endsWith(".pdf")) {

            throw new SmartApplyException(
                    SmartApplyErrorMessage.INVALID_RESUME_FILE
            );
        }

        try {
            // 1. save PDF to disk
            Path uploadPath = Paths.get(uploadDir);
            Files.createDirectories(uploadPath);
            String fileName = user.getId() + "_" + originalFileName;
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath,
                    StandardCopyOption.REPLACE_EXISTING);

            // 2. extract raw text from PDF
            String extractedText = pdfService.extractText(file);

            // print to terminal so we can see what PDFBox reads
            System.out.println("======= PDF TEXT START =======");
            System.out.println(extractedText);
            System.out.println("======= PDF TEXT END =======");

            // 3. save keywords from text to user_skills table
            List<String> skills = skillExtractionService
                    .extractAndSaveSkills(extractedText, user.getId());

            // 4. save resume record to DB
            Resume resume = resumeRepository
                    .findByUserId(user.getId())
                    .orElse(new Resume());
            resume.setUserId(user.getId());
            resume.setFilePath(filePath.toString());
            resume.setExtractedText(extractedText);
            resumeRepository.save(resume);

            return new ResumeResponse(resume.getId(),
                    filePath.toString(),
                    skills);

        } catch (IOException e) {
            throw new SmartApplyException(
                    SmartApplyErrorMessage.RESUME_UPLOAD_FAILED
            );
        }
    }

    @Override
    public ResumeResponse getResumeAnalysis(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new SmartApplyException(
                                SmartApplyErrorMessage.USER_NOT_FOUND
                        )
                );

        Resume resume = resumeRepository.findByUserId(user.getId())
                .orElseThrow(() ->
                        new SmartApplyException(
                                SmartApplyErrorMessage.RESUME_NOT_FOUND
                        )
                );

        List<String> skills = userSkillRepository
                .findByUserId(user.getId())
                .stream()
                .map(UserSkill::getSkill)
                .collect(Collectors.toList());

        return new ResumeResponse(resume.getId(),
                resume.getFilePath(),
                skills);
    }
}