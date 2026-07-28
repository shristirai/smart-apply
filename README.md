# Smart Apply - Backend

## Overview

Smart Apply is an AI-powered Resume Analysis and Job Recommendation System backend built using **Java Spring Boot**.

The backend provides REST APIs for user authentication, resume management, job management, AI-based resume analysis, skill extraction, job matching, and personalized recommendations.

The application uses **Google Gemini AI** for intelligent career suggestions and **Apache PDFBox** for extracting text from uploaded PDF resumes.

---

# Tech Stack

## Backend
- Java 21
- Spring Boot 3
- Spring Security
- JWT Authentication
- Spring Data JPA
- Hibernate ORM
- RESTful APIs
- Maven

## Database
- MySQL 8

## AI Integration
- Google Gemini AI API
- Spring WebClient

## Resume Processing
- Apache PDFBox

## Tools
- IntelliJ IDEA
- Postman
- Git & GitHub

---

# Features

## 1. User Authentication

- User registration
- User login
- JWT based authentication
- Secure API access using Spring Security
- Role-based authorization


## 2. Resume Management

- Upload resume PDF
- Extract resume text using Apache PDFBox
- Extract skills from resume
- Store resume information
- Manage user resume data


## 3. Job Management

- Create job postings
- View available jobs
- Update job details
- Delete job postings
- Store required skills for jobs


## 4. AI Resume Analysis

- Analyze resume content
- Generate career advice using Gemini AI
- Provide improvement suggestions
- Identify relevant skills


## 5. Job Recommendation System

- Match user skills with job requirements
- Calculate match percentage
- Generate personalized job recommendations
- Store recommendation history


## 6. Skill Gap Analysis

- Compare user skills with job requirements
- Identify missing skills
- Suggest skills to learn


---

# Project Architecture

The application follows a **Monolithic Layered Architecture**.

```
Controller Layer
        |
        |
Service Layer
        |
        |
Repository Layer
        |
        |
Database
```

---

# Project Structure

```
src/main/java/com/smartapply/smart_apply

├── config
│   ├── SecurityConfig
│   ├── GeminiConfig
│   └── WebClientConfig
│
├── controller
│   ├── AuthController
│   ├── UserController
│   ├── ResumeController
│   ├── JobController
│   ├── RecommendationController
│   └── GeminiController
│
├── dto
│   ├── request
│   ├── response
│   └── gemini
│
├── exception
│   ├── GlobalExceptionHandler
│   ├── SmartApplyException
│   └── SmartApplyErrorMessage
│
├── model
│   ├── User
│   ├── Resume
│   ├── Job
│   ├── Recommendation
│   └── SkillGap
│
├── repository
│
├── security
│   ├── JwtService
│   ├── JwtFilter
│   └── CustomUserDetailsService
│
├── service
│   ├── AuthService
│   ├── ResumeService
│   ├── JobService
│   ├── MatchingService
│   ├── RecommendationService
│   └── GeminiService
│
└── service.impl
```

---

# Database Design

The application uses a relational database design with normalized tables to manage users, resumes, jobs, skills, and recommendations.

---

## User Table (`users`)

Stores user account information for both job seekers and recruiters.

Fields:

- `id` (Primary Key)
- `fullName`
- `email` (Unique)
- `password`
- `role` (USER / RECRUITER)


---

## Resume Table (`resumes`)

Stores uploaded resume information of users.

Fields:

- `id` (Primary Key)
- `user_id`
- `file_path`
- `extracted_text`

Relationship:

- A user can have resume information associated with their account.


---

## User Skill Table (`user_skills`)

Stores skills extracted from user resumes.

Fields:

- `id` (Primary Key)
- `user_id`
- `skill`

Purpose:

- Stores individual skills of users.
- Used for matching user skills with job requirements.


---

## Job Table (`jobs`)

Stores job posting information created by recruiters.

Fields:

- `id` (Primary Key)
- `title`
- `company`
- `location`
- `description`
- `experience`
- `salary`
- `recruiter_id`


### Job Skills Table (`job_skills`)

Stores required skills for each job.

Fields:

- `job_id` (Foreign Key)
- `skill`


Relationship:

- One job can have multiple required skills.
- A recruiter can create multiple job postings.


---

## Recommendation Table (`recommendations`)

Stores personalized job recommendations generated for users.

Fields:

- `id` (Primary Key)
- `user_id`
- `job_id`
- `match_percentage`
- `recommended_at`


Relationships:

- One user can receive multiple recommendations.
- One job can be recommended to multiple users.


---

# Entity Relationships

```
User
 |
 |---- Resume
 |
 |---- UserSkill
 |
 |---- Recommendation
              |
              |
             Job
              |
              |
        JobSkill


User (Recruiter)
        |
        |
       Job
```

---

# Database Normalization

The database follows **Third Normal Form (3NF)** normalization.

Reasons:

- User information is stored separately from resume and job data.
- Skills are stored in separate tables (`user_skills`, `job_skills`) to avoid data duplication.
- Job recommendation data is maintained separately using relationships between users and jobs.
- Each table represents a single entity with minimal redundancy.

---

# API Endpoints

The backend exposes RESTful APIs for authentication, user management, resume analysis, job management, AI career advice, and job recommendations.

---

# Authentication APIs

## Register User

Creates a new user account.

```
POST /api/auth/register
```

Access:
- Public


## Login User

Authenticates user and returns JWT token.

```
POST /api/auth/login
```

Access:
- Public


---

# User APIs

## Get User Profile

Fetches authenticated user's profile details.

```
GET /api/user/profile
```

Access:
- Authenticated User


---

# Resume APIs

## Upload Resume

Uploads a PDF resume, extracts text, and analyzes skills.

```
POST /api/resume/upload
```

Request:
- Multipart file

Access:
- SEEKER


## Get Resume Analysis

Returns extracted resume information and skill analysis.

```
GET /api/resume/analysis
```

Access:
- SEEKER


---

# Job APIs

## Create Job

Creates a new job posting.

```
POST /api/jobs
```

Access:
- RECRUITER


## Get All Jobs

Fetches all available job postings.

```
GET /api/jobs
```

Access:
- Authenticated User


## Get Job By ID

Fetches job details using job id.

```
GET /api/jobs/{id}
```

Access:
- Authenticated User


## Update Job

Updates an existing job posting.

```
PUT /api/jobs/{id}
```

Access:
- RECRUITER


## Delete Job

Deletes a job posting.

```
DELETE /api/jobs/{id}
```

Access:
- RECRUITER


## Search Jobs By Title

Search jobs using job title.

```
GET /api/jobs/search/title?title={title}
```

Access:
- Authenticated User


## Search Jobs By Company

Search jobs using company name.

```
GET /api/jobs/search/company?company={company}
```

Access:
- Authenticated User


## Search Jobs By Location

Search jobs using location.

```
GET /api/jobs/search/location?location={location}
```

Access:
- Authenticated User


---

# Recommendation APIs

## Generate Job Recommendations

Generates personalized job recommendations based on user's skills.

```
GET /api/recommendations
```

Query Parameters:

```
?page=0&size=5
```

Example:

```
GET /api/recommendations?page=0&size=5
```

Access:
- SEEKER


---

# Gemini AI APIs

## Generate Career Advice

Uses Gemini AI to provide career suggestions based on matched and missing skills.

```
POST /api/gemini/career-advice
```

Request Body:

```json
{
  "matchedSkills": [
    "Java",
    "Spring Boot"
  ],
  "missingSkills": [
    "Docker",
    "AWS"
  ]
}
```

Access:
- SEEKER


---

# API Security

Authentication is implemented using:

- JWT Token based authentication
- Spring Security
- Role-based authorization

Available roles:

```
SEEKER
RECRUITER
```

# Environment Configuration

Create environment variables before running the application.


```
DB_URL=jdbc:mysql://localhost:3306/smart_apply

DB_USERNAME=root

DB_PASSWORD=your_password

JWT_SECRET=your_secret_key

GEMINI_API_KEY=your_gemini_api_key
```

---

# Running the Application

## Clone Repository

```
git clone <repository-url>
```

## Navigate to Backend

```
cd backend
```

## Create Database

```sql
CREATE DATABASE smart_apply;
```

## Run Application

Using Maven:

```
mvn spring-boot:run
```

Application runs on:

```
http://localhost:8080
```

---

# Testing

Run test cases:

```
mvn test
```

API testing:

- Postman

---

# Future Enhancements

- Swagger/OpenAPI Documentation
- Refresh Token Support
- Pagination
- Docker Containerization
- CI/CD Pipeline
- AWS S3 Resume Storage
- Redis Caching
- Unit Testing with JUnit and Mockito
- Integration Testing
- Rate Limiting


---

# Contributors

Smart Apply Development Team

- **Shristi Rai**(@shristirai)
- **Prachi Singh** (@singhscala)
