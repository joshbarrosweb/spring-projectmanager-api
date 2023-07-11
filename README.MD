# Project Manager API REST

The Project Manager is a web application designed to manage projects and users. It provides functionalities to create, update, and delete projects and users, as well as assign and remove users from projects. The application is built using Spring Boot and utilizes a PostgreSQL database for data storage.


## Prerequisites

To run this project, you need to have the following installed:
- Java Development Kit (JDK) 11 or higher
- Docker

## Running the Application

1. Clone the repository:
```bash
    git clone https://github.com/joshbarrosweb/spring-projectmanager-api.git
```


2. Build the application using Gradle:

```bash
    cd spring-projectmanager-api
    ./gradlew clean build
```

3. Start the application and the PostgreSQL database using Docker Compose:
```bash
    docker compose up
```

4. The application will be accessible at [http://localhost:8080](http://localhost:8080).

## Running Tests

To run the automated tests for the application, you can use the following Gradle task:
```bash
    ./gradlew clean test
```

This command will execute the tests and provide a test report with the results.

# API Endpoints

## Project Controller

#### Create a New Project

- **URL:** `/projects`
- **Method:** `POST`
- **Request Body:** ProjectDTO
- **Response:** ProjectDTO

Creates a new project.

#### Get a Paginated List of Projects

- **URL:** `/projects`
- **Method:** `GET`
- **Request Parameters:** None
- **Response:** Page<ProjectDTO>

Retrieves a paginated list of projects.

#### Get a Project by ID

- **URL:** `/projects/{id}`
- **Method:** `GET`
- **Request Parameters:**
  - `id` - ID of the project
- **Response:** ProjectDTO

Retrieves a project by its ID.

#### Update a Project

- **URL:** `/projects/{id}`
- **Method:** `PUT`
- **Request Parameters:**
  - `id` - ID of the project to update
- **Request Body:** ProjectDTO
- **Response:** ProjectDTO

Updates an existing project.

#### Delete a Project

- **URL:** `/projects/{id}`
- **Method:** `DELETE`
- **Request Parameters:**
  - `id` - ID of the project to delete
- **Response:** None

Deletes a project by its ID.

#### Search for Projects by Name

- **URL:** `/projects/search`
- **Method:** `GET`
- **Request Parameters:**
  - `name` - Name to search for
- **Response:** Page<ProjectDTO>

Searches for projects by name.

#### Get Users Associated with a Project

- **URL:** `/projects/{id}/users`
- **Method:** `GET`
- **Request Parameters:**
  - `id` - ID of the project
- **Response:** List<UserDTO>

Retrieves the list of users associated with a project.

### User Controller

#### Create a New User

- **URL:** `/users`
- **Method:** `POST`
- **Request Body:** UserDTO
- **Response:** UserDTO

Creates a new user.

#### Get a Paginated List of Users

- **URL:** `/users`
- **Method:** `GET`
- **Request Parameters:** None
- **Response:** Page<UserDTO>

Retrieves a paginated list of users.

#### Get a User by ID

- **URL:** `/users/{id}`
- **Method:** `GET`
- **Request Parameters:**
  - `id` - ID of the user
- **Response:** UserDTO

Retrieves a user by their ID.

#### Update a User

- **URL:** `/users/{id}`
- **Method:** `PUT`
- **Request Parameters:**
  - `id` - ID of the user to update
- **Request Body:** UserDTO
- **Response:** UserDTO

Updates an existing user.

#### Delete a User

- **URL:** `/users/{id}`
- **Method:** `DELETE`
- **Request Parameters:**
  - `id` - ID of the user to delete
- **Response:** None

Deletes a user by their ID.

#### Search for Users by Name and Email

- **URL:** `/users/search`
- **Method:** `GET`
- **Request Parameters:**
  - `name` - Name to search for
  - `email` - Email to search for
- **Response:** Page<UserDTO>

Searches for users by name and email.

#### Assign a User to a Project

- **URL:** `/users/{userId}/projects/{projectId}`
- **Method:** `POST`
- **Request Parameters:**
  - `userId` - ID of the user
  - `projectId` - ID of the project
- **Response:** None

Assigns a user to a project.

#### Remove a User from a Project

- **URL:** `/users/{userId}/projects/{projectId}`
- **Method:** `DELETE`
- **Request Parameters:**
  - `userId` - ID of the user
  - `projectId` - ID of the project
- **Response:** None

Removes a user from a project.

# API CURL Commands

### Project Controller

#### Create a New Project
curl -X POST -H "Content-Type: application/json" -d '{ "name": "your_project_name", "description": "project_description" }' http://localhost:8080/projects

#### Get a Paginated List of Projects
curl -X GET http://localhost:8080/projects

#### Get a Project by ID
curl -X GET http://localhost:8080/projects/{id}

#### Update a Project
curl -X PUT -H "Content-Type: application/json" -d '{ "name": "new_project_name", "description": "new_project_description" }' http://localhost:8080/projects/{id}

#### Delete a Project
curl -X DELETE http://localhost:8080/projects/{id}

#### Search for Projects by Name
curl -X GET http://localhost:8080/projects/search?name={name}

#### Get Users Associated with a Project
curl -X GET http://localhost:8080/projects/{id}/users

### User Controller

#### Create a New User
curl -X POST -H "Content-Type: application/json" -d '{ "name": "your_name", "email": "your_email" }' http://localhost:8080/users

#### Get a Paginated List of Users
curl -X GET http://localhost:8080/users

#### Get a User by ID
curl -X GET http://localhost:8080/users/{id}

#### Update a User
curl -X PUT -H "Content-Type: application/json" -d '{ "name": "new_name", "email": "new_email" }' http://localhost:8080/users/{id}

#### Delete a User
curl -X DELETE http://localhost:8080/users/{id}

#### Search for Users by Name and Email
curl -X GET http://localhost:8080/users/search?name={name}&email={email}

#### Assign a User to a Project
curl -X POST http://localhost:8080/users/{userId}/projects/{projectId}

#### Remove a User from a Project
curl -X DELETE http://localhost:8080/users/{userId}/projects/{projectId}


Please note that `{id}`, `{userId}`, and `{projectId}` in the URLs should be replaced with the actual IDs of the resources.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

## Contributing

Contributions are welcome! If you find any issues or have suggestions for improvements, please create an issue or submit a pull request.

