# RealtyHub Project
## Overview
RealtyHub is a real estate management platform that allows Admins to register and manage real estate agents (emlakçı), while the agents can create, update, and manage real estate listings. The platform ensures strict role-based permissions, with Admins having control over the system's structure and agents having full control over their listings only.

## Features
### Admin Features:
- Admin Registration: Admin can create an account on the platform.

- Manage Agents: Admin can register, edit, and delete agent accounts, but cannot manage the listings created by agents.

- View Agents: Admin can view the list of all agents but cannot edit or delete agents after their creation.

- No Permission Over Listings: Admin cannot create, update, or delete real estate listings created by agents.

### Agent Features:
- Agent Registration: Admin creates agent accounts, and agents can log in to manage their listings.

- Create Listings: Agents can create real estate listings, including uploading images and providing property details.

- Edit Listings: Agents have the ability to edit their own listings but cannot delete them once created.

- No Access to Admin Features: Agents cannot access the admin interface, nor can they manage other agents or admin accounts.

## Technology Stack
- Backend: Java with Spring Boot

- Database: PostgreSQL

- Frontend: (HTML, JavaScript, Thymeleaf, etc.)

- Authentication: JWT-based token authentication

- Docker: Dockerized for easy deployment and scalability

- CI/CD: GitHub Actions for automated testing and deployment

## System Workflow
### 1. Admin Registration:

- Admin registers by filling in the required details.

- Admin gets full access to register and manage agents.

### 2. Agent Registration:

- Admin creates an agent account.

- After registration, the agent can log in and start creating, updating, and managing their own listings.

### 3. Agent Listings:

- Agents can create listings for real estate properties, including uploading images and entering property details.

- They can also update their listings but cannot delete them.

### 4. Role-Based Access Control (RBAC):

- Admins cannot manage or interact with listings created by agents.

- Admins can only view and verify the presence of agents in the system.

- Agents cannot access admin functionalities.

## Installation & Setup
### Prerequisites
- JDK 17 (or higher)

- Docker (optional for containerization)

- PostgreSQL or Dockerized database

### 1. Clone the Repository
#### bash console
- git clone https://github.com/yourusername/realtyhub.git
- cd realtyhub

### 2. Install Dependencies
### Using Maven:
#### bash console
- mvn clean install

### 3. Setup Database
Ensure PostgreSQL is running with the following configurations:

- Database Name: DATABASE_NAME

- Username: POSTGRES_USERNAME

- Password: POSTGRES_PASSWORD

Alternatively, use the Dockerized PostgreSQL configuration in docker-compose.yml.

### 4. Run the Application
If you're using Docker:

#### bash console
- docker-compose up --build

Alternatively, run the app locally:

#### bash console
- mvn spring-boot:run
## Role-Based Access and Security
The RealtyHub system strictly enforces role-based access control (RBAC). Here's a breakdown of permissions:

- Admin:
  - Can manage agents.
  - Can view but not edit or delete agent-created listings.
  - Cannot create, edit, or delete real estate listings.

- Agent:
  - Can create and edit their own listings.
  - Cannot interact with listings created by other agents or admins.
  - Cannot delete their listings once they are created.

## CI/CD
This project uses GitHub Actions for Continuous Integration and Continuous Deployment (CI/CD). On every commit and pull request, the following actions are performed:

- Build: The application is compiled and built.

- Test: Unit tests are run automatically.

- Docker Build: The application is Dockerized and an image is created.

- Push to Docker Hub: The Docker image is pushed to Docker Hub.

### Workflow Example (ci-cd.yml)
```
name: CI/CD Pipeline

on:
  push:
    branches: [ "main", "develop" ]
  pull_request:
    branches: [ "main", "develop" ]


jobs:
  build-and-deploy:
    runs-on: ubuntu-latest
    
    services:
      postgres:
        image: postgres
        env:
          POSTGRES_USER: POSTGRES_USER
          POSTGRES_PASSWORD: POSTGRES_PASSWORD
          POSTGRES_DB: POSTGRES_DB
        ports:
          - 5432:5432

    steps:
      - name: Checkout Code
        uses: actions/checkout@v2

      - name: Set up JDK 17
        uses: actions/setup-java@v2
        with:
          java-version: '17'

      - name: Build and Run Tests
        run: mvn clean install --no-transfer-progress

      - name: Build Docker Image
        run: docker build -t ${{ secrets.DOCKER_USERNAME }}/realtyhub:latest .

      - name: Push Docker Image to Docker Hub
        run: docker push ${{ secrets.DOCKER_USERNAME }}/realtyhub:latest
```


    
## Contributing
If you want to contribute to the RealtyHub project:

- Fork the repository and create your branch.

- Commit your changes and create a pull request.

- Ensure all tests pass before submitting your PR.

