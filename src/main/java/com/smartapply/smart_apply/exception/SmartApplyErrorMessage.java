package com.smartapply.smart_apply.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SmartApplyErrorMessage {

    // Authentication
    EMAIL_ALREADY_EXISTS("AUTH_001", "Email is already registered. Please log in or use a different email."),
    INVALID_CREDENTIALS("AUTH_002", "Invalid email or password."),
    UNAUTHORIZED_ACCESS("AUTH_003", "You are not authorized to access this resource."),

    // User
    USER_NOT_FOUND("USR_001", "User not found."),
    RECRUITER_NOT_FOUND("USR_002", "Recruiter not found."),

    // Resume
    RESUME_NOT_FOUND("RES_001", "Resume not found."),
    INVALID_RESUME_FILE("RES_002", "Please upload a valid PDF resume."),
    RESUME_UPLOAD_FAILED("RES_003", "Failed to upload the resume. Please try again."),
    PDF_EXTRACTION_FAILED("RES_004", "Unable to extract text from the uploaded resume."),
    FILE_SIZE_EXCEEDED("RES_005", "Resume size must not exceed 10 MB"),

    // Job
    JOB_NOT_FOUND("JOB_001", "Job not found."),
    JOB_UPDATE_NOT_ALLOWED("JOB_002", "You are not authorized to update this job."),
    JOB_DELETE_NOT_ALLOWED("JOB_003", "You are not authorized to delete this job."),

    // Recommendation
    RECOMMENDATION_NOT_FOUND("REC_001", "Recommendation not found."),

    // Gemini AI
    GEMINI_API_ERROR("AI_001", "Unable to generate AI recommendations at the moment. Please try again later."),
    GEMINI_RESPONSE_PARSE_ERROR("AI_002", "Failed to process the AI response."),

    // Validation
    INVALID_REQUEST("REQ_001", "Invalid request. Please check the submitted data."),

    // Common
    INTERNAL_SERVER_ERROR("SYS_001", "An unexpected error occurred. Please try again later.");

    private final String errorCode;
    private final String errorMessage;
}