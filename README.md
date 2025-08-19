# 🚀 Spring Boot Beginner Projects

This repository contains **3 beginner-friendly Spring Boot projects** I built to learn and practice real-world backend concepts like REST APIs, database integration, authentication, WebSockets, GraphQL, and caching.

I made these projects for **anyone who wants to learn good project structure and clean code practices** with Spring Boot.  
Each project is simple but demonstrates **important building blocks** for backend development.

---

## 📌 Projects

### 1️⃣ TodoApp (Task Manager with JWT Security)
A task management API with user authentication.

- **Tech Stack**: Spring Boot, Spring Security (JWT), Spring Data JPA, PostgreSQL, Flyway
- **Features**:
    - User registration & login with JWT
    - CRUD operations on tasks (create, read, update, delete)
    - Task ownership per user
    - Error handling with custom exceptions
- **Highlights**:
    - Dockerized PostgreSQL setup
    - Global exception handling with clean JSON responses

---

### 2️⃣ ChatApp (WebSocket Messaging)
A real-time chat application using WebSockets.

- **Tech Stack**: Spring Boot, Spring WebSocket, STOMP, SockJS
- **Features**:
    - Users can join chat rooms
    - Send/receive real-time messages
    - Basic user presence (who’s online)
- **Highlights**:
    - WebSocket messaging with Spring
    - Simple frontend (JS/HTML) to test chat
    - Clean separation of DTOs and services

---

### 3️⃣ SocialMedia Backend (GraphQL + Caching)
A lightweight **social media backend API** built with GraphQL for flexible querying and caching for fast data retrieval.

- **Tech Stack**: Spring Boot, Spring Data JPA, GraphQL (Spring for GraphQL), Redis (for caching)
- **Features**:
    - User profiles, posts, and comments
    - Query posts by user or timeline with GraphQL
    - Mutations for creating posts & comments
    - Redis caching for frequently requested data (e.g., trending posts)
- **Highlights**:
    - GraphQL schema-first API design
    - Optimized performance with Redis cache
    - Pagination & filtering with GraphQL queries

---

## 🚀 Getting Started

### Prerequisites
- Java 17+
- Maven or Gradle
- Docker (for PostgreSQL in TodoApp, Redis in SocialMedia)

### Running the Apps

1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/spring-boot-beginner-projects.git
   cd spring-boot-beginner-projects
