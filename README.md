# Redis Rate Limiter

A Spring Boot application demonstrating rate limiting using Redis. This project implements a rate limiter that restricts the number of requests per time window using Redis as the backend storage.

## Features

- Rate limiting using Redis
- Configurable request limits and time windows
- Spring Boot 3.2.5
- Java 21

## Prerequisites

- Java 21
- Maven 3.6+
- Redis server running locally or accessible

## Configuration

The rate limiter is configured with:
- Maximum requests: 10 per window
- Time window: 60 seconds

You can configure Redis connection in `application.properties` or `application.yml`.

## Running the Application

1. Make sure Redis is running on your machine
2. Build the project:
   ```bash
   ./mvnw clean install
   ```
3. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```

## API Endpoints

- `GET /api/test` - Test endpoint protected by rate limiting

## How It Works

The rate limiter uses Redis to track the number of requests per key (typically IP address or user ID) within a sliding time window. When a request exceeds the limit, it is rejected.

