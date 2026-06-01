# SpringMart

SpringMart is a secure and extensible e-commerce backend built with Spring Boot.

The project provides a RESTful API for authentication, user management, product catalog management, shopping carts, and order processing. It follows a layered architecture and uses JWT-based authentication, role-based authorization, validation, exception handling, and OpenAPI documentation.

---

## Features

### Authentication & Security

- JWT Access Token authentication
- Refresh Token support
- Logout from current device
- Logout from all devices
- Stateless security architecture
- Role-based authorization (USER / ADMIN)
- Custom authentication and authorization handlers

### User Management

- User registration
- User profile retrieval
- User profile updates
- User account administration

### Product Catalog

- Create products
- Update products
- Delete products
- Browse products
- Search products by name
- Filter products by category
- Pagination support

### Categories

- Create categories
- Update categories
- Delete categories
- Retrieve category details
- Hierarchical category structure

### Shopping Cart

- Add products to cart
- Remove products from cart
- Clear cart
- View cart contents
- Convert cart into an order

### Orders

- Create orders from shopping cart
- Cancel orders
- Mark orders as paid
- Mark orders as shipped
- View order history

---

## Technology Stack

### Backend

- Java 21
- Spring Boot 4
- Spring Web
- Spring Security
- Spring Data JPA

### Database

- PostgreSQL
- Hibernate ORM

### Authentication

- JWT (JJWT)

### Mapping

- MapStruct
- Lombok

### Documentation

- Springdoc OpenAPI
- Swagger UI

### Build Tool

- Maven

---

## Architecture

SpringMart follows a layered architecture:

```text
Controller Layer
    ↓
Service Layer
    ↓
Repository Layer
    ↓
PostgreSQL Database
```

Key architectural principles:

- DTOs separate API contracts from persistence models
- Business logic resides in the service layer
- Repositories handle persistence concerns
- JWT authentication is implemented through a custom security filter
- Method-level authorization is enforced using `@PreAuthorize`

---

## Security

Protected endpoints require a JWT access token.

Example:

```http
Authorization: Bearer <access_token>
```

Security features include:

- Stateless authentication
- JWT validation filter
- Refresh token workflow
- Access denied handling
- Authentication entry point handling
- Role-based endpoint protection

---

## API Documentation

Interactive API documentation is available through Swagger UI.

### Swagger UI

`http://localhost:8080/swagger-ui/index.html`

### OpenAPI Specification

`http://localhost:8080/v3/api-docs`

Documented API modules:

- Authentication
- Users
- Products
- Categories
- Shopping Cart
- Orders

---

## Running the Application

### Prerequisites

- Java 21+
- Maven 3.9+
- PostgreSQL

### Configuration

Configure your database and JWT settings in:

`src/main/resources/application.properties`

Example properties:

- spring.datasource.url
- spring.datasource.username
- spring.datasource.password
- spring.jpa.hibernate.ddl-auto
- jwt.secret

### Build

```bash
mvn clean package
```

### Run

```bash
mvn spring-boot:run
```

or

```bash
java -jar target/SpringMart-1.0-SNAPSHOT.jar
```

---

## Testing

Run all tests:

```bash
mvn test
```

---

## Future Improvements

- Docker support
- Integration testing
- Testcontainers
- Redis caching
- Product image storage
- Payment gateway integration
- Order shipment tracking
- CI/CD pipeline

---

## License

This project is licensed under the MIT License. See the LICENSE file for details.

---

## Author

Mohammad Momensafaei

GitHub: https://github.com/mohammad2116
