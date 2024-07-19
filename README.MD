# Flight Service Application

This is the Flight Service application for managing flight information. It is built with Spring Boot and uses PostgreSQL for database management. This guide will help you set up and run the application using Docker.

## Prerequisites

- Docker
- Docker Compose
- Git

## Setup

1. **Create a directory for the project:**

    ```sh
    mkdir air-astana
    ```

    ```sh
    cd air-astana
    ```
   

2. **Clone the repository:**

    ```sh
    git clone https://github.com/Yesmukhanov/flight-service.git
    ```

3. **Navigate to the project directory:**

    ```sh
    cd flight-service
    ```

## Running the Application

1. **Build and start the Docker containers:**

    ```sh
    docker-compose up -d
    ```

   This command will start the PostgreSQL database and the Spring Boot application in detached mode.

## Application Endpoints

### Registration and Authentication

- **Register a new user:**

    ```http
    POST /auth/register
    ```
  
    ```curl
      curl --location 'http://localhost:8080/api/v1/auth/register' \
        --header 'Content-Type: application/json' \
        --data '{
        "username": "",
        "password": ""
        }'
    ```

  Example request body:

    ```json
    {
        "username": "newuser",
        "password": "newpassword"
    }
    ```
    
    ```curl
    curl --location 'http://localhost:8080/api/v1/auth/authentication' \
        --header 'Content-Type: application/json' \
        --data '{
        "username": "",
        "password": ""
        }'
    ```

  Example response:

    ```json
    {
        "accessToken": "jwt-access-token",
        "refreshToken": "jwt-refresh-token"
    }
    ```

  - **Authenticate a user:**

      ```http
      POST /auth/authenticate
      ```

    Example request body:

      ```json
      {
          "username": "existinguser",
          "password": "existingpassword"
      }
      ```
      ```info 
          i'm added user moderator by body it's default user role MODERATOR
      {
          "username": "moderator",
          "password": "moderator"
      }
      ```

    Example response:

      ```json
      {
          "accessToken": "jwt-access-token",
          "refreshToken": "jwt-refresh-token"
      }
      ```

- **Refresh token:**

    ```http
    POST /auth/refresh-token
    ```

  Example request body:

    ```json
    {
        "refreshToken": "jwt-refresh-token"
    }
    ```

  Example response:

    ```json
    {
        "accessToken": "new-jwt-access-token",
        "refreshToken": "existing-jwt-refresh-token"
    }
    ```

### Flights

- **Get all flights:**

    ```http
    GET /flights
    ```
    ```curl
        curl --location 'http://localhost:8080/api/v1/flight' \
            --header 'Content-Type: application/json' \
            --data '{
            "origin": "Ak",
            "destination": "Semey",
            "departure": "2024-07-20T10:15:30+06:00",
            "arrival": "2024-07-20T23:15:30+06:00",
            "flightStatus": "InTime"
             }
        '
    ```

  You can filter flights by origin and/or destination using query parameters:

    ```http
    GET /flights
    ```
    ```curl
        curl --location 'http://localhost:8080/api/v1/flight'
    ```
  Example request body:

    ```json
    {
      "origin": "Aktobe",
      "destination": "Semey",
      "departure": "2024-07-20T10:15:30+06:00",
      "arrival": "2024-07-20T23:15:30+06:00",
      "flightStatus": "InTime"
     }
    ``` 
  You can changes status flights by id:

    ```http
    PUT /flights/{flightId}?status=Delayed
    ```
    ```curl
        curl --location --request PUT 'http://localhost:8080/api/v1/flight/1?status=Cancelled' \
            --data ''
    ```
    

## Project Structure

- `src/main/java/kz/air_astana/flight_service/` - Main application source code
- `src/main/resources/` - Configuration files
- `Dockerfile` - Docker image build instructions
- `docker-compose.yml` - Docker Compose configuration

## Technologies Used

- Spring Boot
- PostgreSQL
- Docker
- Docker Compose


