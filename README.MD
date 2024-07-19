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
        "username": "newuser",
        "password": "newpassword"
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

    ```curl
    curl --location 'http://localhost:8080/api/v1/auth/authenticate' \
        --header 'Content-Type: application/json' \
        --data '{
        "username": "existinguser",
        "password": "existingpassword"
        }'
    ```

  Example response:

    ```json
    {
        "accessToken": "jwt-access-token",
        "refreshToken": "jwt-refresh-token"
    }
    ```

  > **Note**: The application includes a default user with the role `MODERATOR`:
  > ```json
    > {
    >     "username": "moderator",
    >     "password": "moderator"
    > }
    > ```

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

  Example request:

    ```curl
    curl --location 'http://localhost:8080/api/v1/flight' \
        --header 'Content-Type: application/json'
    ```

  Example response:

    ```json
    [
        {
            "id": 1,
            "origin": "Almaty",
            "destination": "Semey",
            "departure": "2024-07-20T10:15:30+06:00",
            "arrival": "2024-07-20T23:15:30+06:00",
            "flightStatus": "InTime"
        },
        {
            "id": 2,
            "origin": "Aktobe",
            "destination": "Semey",
            "departure": "2024-07-20T10:15:30+06:00",
            "arrival": "2024-07-20T23:15:30+06:00",
            "flightStatus": "Delayed"
        }
    ]
    ```

- **Add a new flight:**

    ```http
    POST /flights
    ```

  Example request:

    ```curl
    curl --location 'http://localhost:8080/api/v1/flight' \
        --header 'Content-Type: application/json' \
        --data '{
        "origin": "Aktobe",
        "destination": "Semey",
        "departure": "2024-07-20T10:15:30+06:00",
        "arrival": "2024-07-20T23:15:30+06:00",
        "flightStatus": "InTime"
        }'
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

- **Update flight status by ID:**

    ```http
    PUT /flights/{flightId}?status=Delayed
    ```

  Example request:

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


