# SpringMart

[![SpringMart CI](https://github.com/Mohammad2116/springMart/actions/workflows/ci.yml/badge.svg)](https://github.com/Mohammad2116/springMart/actions/workflows/ci.yml)

![Java](https://img.shields.io/badge/Java-21-blue)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.5-brightgreen)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue)
![Redis](https://img.shields.io/badge/Redis-Caching-red)
![Docker](https://img.shields.io/badge/Docker-Containerized-blue)
![Render](https://img.shields.io/badge/Render-Deployed-success)
![License](https://img.shields.io/badge/License-MIT-green)

> Production-ready e-commerce backend built with Spring Boot, JWT Authentication, PostgreSQL, Redis, Flyway, Docker, GitHub Actions CI, and deployed on Render.



## Overview

SpringMart is a modern e-commerce backend application built with Spring Boot.

The project demonstrates real-world backend engineering practices including:

- JWT-based Authentication
- Role-based Authorization
- RESTful API Design
- Global Exception Handling
- Validation
- Database Versioning with Flyway
- Redis Caching
- Docker Containerization
- CI/CD using GitHub Actions
- Cloud Deployment on Render

The goal of this project is to showcase production-oriented backend development using the Spring ecosystem.

---

## Features

### Authentication & Security

- JWT Access Token Authentication
- Refresh Token Support
- Logout Current Session
- Logout All Sessions
- Stateless Security Architecture
- Role-Based Authorization (USER / ADMIN)
- Account Lock / Unlock Support
- Global Exception Handling
- Request Validation

### User Management

- User Registration
- User Profile Retrieval
- User Profile Update
- Administrative User Management
- User Lock / Unlock Operations

### Product Management

- Create Products
- Update Products
- Delete Products
- Product Search
- Category Filtering
- Pagination & Sorting
- Soft Delete Support

### Category Management

- Create Categories
- Update Categories
- Delete Categories
- Category Details Retrieval
- Full Category View
- Redis-Cached Category Queries

### Shopping Cart

- Add Product to Cart
- Remove Product from Cart
- Clear Cart
- View Cart
- Convert Cart into Order

### Order Management

- Create Orders
- View Order History
- Cancel Orders
- Mark Orders as Paid
- Mark Orders as Shipped

---

## Technology Stack

### Backend

- Java 21
- Spring Boot 3.5
- Spring Web
- Spring Security
- Spring Data JPA
- Hibernate ORM

### Database

- PostgreSQL
- Flyway Migration Tool

### Cache

- Redis
- Spring Cache

### API Documentation

- OpenAPI 3
- Swagger UI

### Build Tools

- Maven
- Lombok
- MapStruct

### DevOps

- Docker
- Multi-Stage Docker Build
- GitHub Actions
- Render

---

## Architecture

```text
Client
  ↓
Controllers
  ↓
Services
  ↓
Redis Cache
  ↓
Repositories
  ↓
PostgreSQL
```

### Architectural Principles

- Layered Architecture
- Separation of Concerns
- DTO-based API Contracts
- Service-Layer Business Logic
- Repository Pattern
- Stateless Authentication
- Method-Level Authorization
- Centralized Error Handling
- Validation at API Boundaries

### Architecture Highlights

- Soft Delete using Hibernate
- Redis Cache Integration
- Flyway Database Versioning
- OpenAPI Documentation
- Dockerized Deployment
- CI Pipeline with GitHub Actions

---

## Redis Caching

SpringMart uses Redis through Spring Cache abstraction.

Currently cached resources:

- Category Details
- Category Full Details

Implemented cache operations:

- Cache Read (`@Cacheable`)
- Cache Update (`@CachePut`)
- Cache Eviction (`@CacheEvict`)

---

## CI/CD

GitHub Actions automatically:

- Builds the application
- Executes tests
- Validates Maven build integrity
- Prevents broken code from reaching the main branch

Workflow status is displayed by the badge at the top of this README.

---

## Running Locally

### Prerequisites

- Java 21+
- Maven 3.9+
- PostgreSQL
- Redis

### Build

```bash
mvn clean package
```

### Run

```bash
mvn spring-boot:run
```

---

## Running with Docker

### Build Image

```bash
docker build -t springmart .
```

### Run Application

```bash
docker run -p 8080:8080 springmart
```

### Run Redis

```bash
docker run -d --name redis -p 6379:6379 redis:latest
```

### Multi-Stage Docker Build

The project uses a multi-stage Docker build to reduce image size and improve deployment efficiency.

---

## Database Versioning

Flyway is used to manage schema evolution.

Benefits:

- Version-controlled database changes
- Repeatable deployments
- Safer production releases
- Consistent environments

---

## Deployment

The application is deployed on Render using Docker.

Production URL:

https://springmart-backend-v2ux.onrender.com

Swagger URL:

https://springmart-backend-v2ux.onrender.com/swagger-ui/index.html

---

## Testing

Run tests:

```bash
mvn test
```

The Docker build process executes the test suite before packaging the application.

---

## Future Improvements

- Testcontainers Integration
- Product Image Storage
- Payment Gateway Integration
- Shipment Tracking
- Email Notifications
- Audit Logging
- Redis Caching for Paginated Responses
- Monitoring with Spring Boot Actuator

---

## License

MIT License

---

## Author

Mohammad Momensafaei

GitHub:
https://github.com/Mohammad2116
