package com.smartapply.smart_apply.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SmartApplyErrorMessage {

    // Authentication
    EMAIL_ALREADY_EXISTS("AUTH_001", "Email already registered"),
    INVALID_CREDENTIALS("AUTH_002", "Invalid email or password"),
    UNAUTHORIZED_ACCESS("AUTH_003", "Unauthorized access"),

    // User
    USER_NOT_FOUND("USR_001", "User not found"),
    RECRUITER_NOT_FOUND("USR_002", "Recruiter not found"),

    // Resume
    RESUME_NOT_FOUND("RES_001", "Resume not found"),
    INVALID_RESUME_FILE("RES_002", "Invalid resume file"),
    RESUME_UPLOAD_FAILED("RES_003", "Failed to upload resume"),
    PDF_EXTRACTION_FAILED("RES_004", "Failed to extract text from PDF"),

    // Job
    JOB_NOT_FOUND("JOB_001", "Job not found"),
    JOB_UPDATE_NOT_ALLOWED("JOB_002", "You are not authorized to update this job"),
    JOB_DELETE_NOT_ALLOWED("JOB_003", "You are not authorized to delete this job"),

    // Recommendation
    RECOMMENDATION_NOT_FOUND("REC_001", "Recommendation not found"),

    // Gemini AI
    GEMINI_API_ERROR("AI_001", "Failed to generate AI response"),
    GEMINI_RESPONSE_PARSE_ERROR("AI_002", "Failed to parse AI response"),

    // Validation
    INVALID_REQUEST("REQ_001", "Invalid request"),

    // Common
    INTERNAL_SERVER_ERROR("SYS_001", "Internal server error");

    private final String errorCode;
    private final String errorMessage;
}