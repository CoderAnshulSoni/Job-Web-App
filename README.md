# Job Web Application

A full-stack web application for job seekers and recruiters to connect and manage job postings. Built with Spring Boot backend and Thymeleaf templating for the frontend.

## Overview

This is a comprehensive job portal platform that enables:
- **Job Seekers**: Search for jobs, view job details, save jobs, and manage profiles
- **Recruiters**: Post job listings, manage postings, and view job seeker profiles

## Technology Stack

### Backend
- **Framework**: Spring Boot 3.4.3
- **Language**: Java 23
- **Database**: MySQL
- **ORM**: Spring Data JPA
- **Security**: Spring Security with Thymeleaf integration
- **Validation**: Spring Validation

### Frontend
- **Template Engine**: Thymeleaf
- **UI Framework**: Bootstrap 5.3.3
- **jQuery**: 3.7.1
- **Icons**: Font Awesome 6.7.2
- **Build Tool**: Maven


## Key Features

### For Job Seekers
- 🔍 **Job Search**: Global search functionality to find relevant job postings
- 💼 **Job Details**: Comprehensive job information including requirements and responsibilities
- 📌 **Save Jobs**: Save favorite job postings for later review
- 👤 **Profile Management**: Create and manage job seeker profile with skills and experience

### For Recruiters
- ✍️ **Post Jobs**: Easy-to-use form to create and publish new job listings
- 📊 **Dashboard**: View and manage all posted jobs
- 👥 **Candidate Profiles**: Browse and review job seeker profiles
- 📋 **Job Management**: Update or remove job postings

### General Features
- 🔐 **Authentication & Authorization**: Secure login and role-based access control
- 📱 **Responsive Design**: Mobile-friendly Bootstrap UI
- ✅ **Input Validation**: Server-side validation for data integrity
- 🗄️ **Database**: MySQL-backed persistence layer

## Database

- **Database**: MySQL (jobportal)
- **Default Credentials**: 
  - Username: `jobportal`
  - Password: `jobportal`
  - Host: `localhost:3306`

## Getting Started

### Prerequisites
- Java 23 or higher
- Maven 3.6+
- MySQL 8.0+

### Installation

1. Clone the repository
   git clone https://github.com/CoderAnshulSoni/Job-Web-App.git
   cd Job-Web-App

2. Set up MySQL database
  CREATE DATABASE jobportal;

3. Update database credentials in src/main/resources/application.properties if needed

4. Build and run the application
    mvn clean install
    mvn spring-boot:run

5. Access the application at http://localhost:8080


Note: This README provides a comprehensive overview of your Job Web Application, covering its purpose, technology stack, project structure, features, and setup instructions. Feel free to customize it further based on your specific needs!
