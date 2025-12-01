# user-mail

A multi-module project with microservices for user management and email delivery.

## Summary

This repository contains microservices for managing users and sending emails. The `User_InGress` module provides a REST API for user CRUD operations and publishes events (RabbitMQ) when users are created. The services use Spring Boot, PostgreSQL, and RabbitMQ.

## Tech stack

- Java 17
- Spring Boot (3.x)
  - Spring Web
  - Spring Data JPA
  - Spring AMQP (RabbitMQ)
  - Spring Mail
  - Spring Validation
- PostgreSQL (runtime DB)
- RabbitMQ (message broker)
- Flyway (DB migrations in some modules)
- springdoc-openapi (Swagger UI) for API documentation
- Maven (wrapper included: `mvnw`, `mvnw.cmd`)

## Main module

The `User_InGress` module contains the user management REST API and can be run independently for local development.

- Path: `User_InGress`
- Main controller: `johnatanSSP.user_InGress.controller.UserController`
- Service: `johnatanSSP.user_InGress.service.UserService`

## API routes (documented)

The following endpoints are available in `UserController`:

- POST /user
  - Description: Create a new user and publish an event.
  - Request body: JSON matching the `UserDto` fields (name, email, etc.).
  - Success: 201 Created
  - Example cURL:

```powershell
curl -X POST http://localhost:8080/user -H "Content-Type: application/json" -d '{"name":"John","email":"john@example.com"}'
```

- GET /all/users
  - Description: Return a list of all users.
  - Success: 200 OK
  - Example cURL:

```powershell
curl http://localhost:8080/all/users
```

- DELETE /user/{id}
  - Description: Delete a user by UUID.
  - Success: 204 No Content
  - Example cURL:

```powershell
curl -X DELETE http://localhost:8080/user/<uuid>
```

> Note: The exact JSON shape for `UserDto` and the required fields are defined in the module's `dto` package.

## API documentation (Swagger / OpenAPI)

After starting the application, API docs are available via springdoc-openapi:

- Swagger UI: http://localhost:8080/swagger-ui.html  (or `/swagger-ui/index.html`)
- OpenAPI JSON: http://localhost:8080/v3/api-docs

## How to run locally (development)

1. Configure application properties:
   - Edit `User_InGress/src/main/resources/application.yml` to set database URL, username, password and RabbitMQ settings, or use the provided Docker compose files to run dependencies.

2. Start dependent services with Docker (optional):
   - If you prefer Docker for PostgreSQL and RabbitMQ, check the repository's `docker-compose.yml` files in the root modules. Example (run from repo root if a compose file is available):

```powershell
# from repository root if there is a prepared docker-compose
docker-compose up -d
```

3. Build and run the `User_InGress` module with Maven wrapper:

```powershell
cd "C:\Users\johna\IdeaProjects\user-mail\User_InGress"
# build the module (skip tests for faster iteration)
.\mvnw.cmd -DskipTests package
# run the application
.\mvnw.cmd -DskipTests spring-boot:run
```

The app should start on port 8080 by default (unless configured otherwise in `application.yml`).

## Quick tests

Use the example cURL commands above or your API client (Postman / Insomnia) to exercise the endpoints. Also check the Swagger UI to see the request/response models.

## Notes and next steps

- If you want more consistent RESTful naming, consider renaming `/all/users` to `/users` (GET) and `/user` to `/users` (POST) and `/users/{id}` (DELETE).
- You can enhance OpenAPI metadata by adding an `OpenAPI` bean with title, version, and contact information in a configuration class.
- If you want, I can add a short `docker-compose.yml` snippet for running PostgreSQL and RabbitMQ for local development.

---

If you want, I can also:
- add examples of `application.yml` values for local development,
- rename routes to standard REST conventions,
- add an OpenAPI info configuration.
