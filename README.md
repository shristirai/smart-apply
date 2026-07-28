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

## User Table

Stores user account details.

Fields:
- id
- name
- email
- password
- role


## Resume Table

Stores uploaded resume information.

Fields:
- id
- fileName
- extractedText
- skills
- user_id


## Job Table

Stores job details.

Fields:
- id
- title
- company
- description
- requiredSkills


## Recommendation Table

Stores generated recommendations.

Fields:
- id
- matchPercentage
- user_id
- job_id


## SkillGap Table

Stores missing skills information.

Fields:
- id
- missingSkills
- recommendation_id


---

# API Endpoints

## Authentication APIs

### Register User

```
POST /api/auth/register
```

### Login User

```
POST /api/auth/login
```


---

## Resume APIs

### Upload Resume

```
POST /api/resume/upload
```

### Get Resume

```
GET /api/resume/{id}
```


---

## Job APIs

### Create Job

```
POST /api/jobs
```

### Get All Jobs

```
GET /api/jobs
```

### Get Job By Id

```
GET /api/jobs/{id}
```


---

## Recommendation APIs

### Generate Recommendation

```
POST /api/recommendations/generate
```

### Get User Recommendations

```
GET /api/recommendations/user/{userId}
```


---

## Gemini AI APIs

### Generate Career Advice

```
POST /api/gemini/career-advice
```

---

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
