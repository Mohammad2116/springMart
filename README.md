# SpringMart

> Production-ready e-commerce backend built with Spring Boot, JWT Authentication, PostgreSQL, Docker, and deployed on Render.

## Project Highlights

- Java 21
- Spring Boot 3.5
- Spring Security
- JWT Authentication & Refresh Tokens
- Role-Based Authorization (USER / ADMIN)
- PostgreSQL
- Spring Data JPA & Hibernate
- OpenAPI 3 / Swagger UI
- Dockerized (Multi-Stage Build)
- Cloud Deployment on Render
- Global Exception Handling
- Request Validation
- Production-Ready REST API

---

## Live Demo

### Production API

https://springmart-backend-v2ux.onrender.com

### Swagger Documentation

https://springmart-backend-v2ux.onrender.com/swagger-ui/index.html

---

## Overview

SpringMart is a secure and extensible e-commerce backend built with Spring Boot.

The project provides a RESTful API for authentication, user management, product catalog management, shopping carts, and order processing. It follows modern backend development practices including JWT-based authentication, role-based authorization, validation, exception handling, API documentation, containerization, and cloud deployment.

The primary goal of this project is to demonstrate production-ready backend development using the Spring ecosystem.

---

## Features

### Authentication & Security

- JWT Access Token Authentication
- Refresh Token Support
- Logout from Current Device
- Logout from All Devices
- Stateless Security Architecture
- Role-Based Authorization (USER / ADMIN)
- Account Lock / Unlock Management
- Global Exception Handling
- Request Validation

### User Management

- User Registration
- User Profile Retrieval
- User Profile Updates
- Administrative User Management
- User Account Locking / Unlocking

### Product Catalog

- Create Products
- Update Products
- Delete Products
- Browse Products
- Search Products by Name
- Filter Products by Category
- Pagination Support

### Categories

- Create Categories
- Update Categories
- Delete Categories
- Hierarchical Category Structure

### Shopping Cart

- Add Products to Cart
- Remove Products from Cart
- Clear Cart
- View Cart Contents
- Convert Cart into Orders

### Orders

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

### Authentication

- JWT (JJWT)

### Documentation

- OpenAPI 3
- Swagger UI

### Development Tools

- Maven
- Lombok
- MapStruct

### Deployment

- Docker
- Multi-Stage Docker Build
- Render Cloud Platform

---

## Architecture

```text
Client
  ↓
Controllers
  ↓
Services
  ↓
Repositories
  ↓
PostgreSQL
```

### Architectural Principles

- Clear separation of concerns
- DTO-based API contracts
- Service-layer business logic
- Repository abstraction for persistence
- Stateless JWT authentication
- Method-level authorization using `@PreAuthorize`
- Centralized exception handling
- Validation at API boundaries

---

## API Documentation

### Production Swagger UI

https://springmart-backend-v2ux.onrender.com/swagger-ui/index.html

### Local Swagger UI

http://localhost:8080/swagger-ui/index.html

---

## Running Locally

### Prerequisites

- Java 21+
- Maven 3.9+
- PostgreSQL

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

### Build Docker Image

```bash
docker build -t springmart .
```

### Run Docker Container

```bash
docker run -p 8080:8080 springmart
```

### Multi-Stage Build

The project uses a multi-stage Docker build to reduce image size and improve deployment efficiency.

---

## Deployment

SpringMart is deployed on Render using Docker.

Production URL:

https://springmart-backend-v2ux.onrender.com

---

## Testing

```bash
mvn test
```

The Docker build process executes the test suite before packaging the application.

---

## Future Improvements

- Redis Caching
- Testcontainers Integration
- CI/CD Pipeline
- Product Image Storage
- Payment Gateway Integration
- Order Shipment Tracking
- Email Notifications
- Audit Logging

---

## License

MIT License

---

## Author

Mohammad Momensafaei

GitHub:
https://github.com/Mohammad2116
