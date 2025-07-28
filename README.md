# GitHubApi

GitHubApi is a Java Spring Boot application that exposes a REST API to retrieve all non-fork GitHub repositories for a
given user, including their branches and the last commit SHA.
The application runs by default on http://localhost:8080.

## Technologies:

- Java 21
- Spring Boot 3.5
- REST API using SPRING MVC
- RestTemplate
- Junit 5

## Prerequisites

- Java 21

## Setup

1. Clone the repository:

```bash
git clone https://github.com/AdrianRuchala/GitHubApi.git
```

2. Go to the project folder:

```bash
cd GitHubApi
```

## Run application

In the project folder run the app with:

```bash
./gradlew bootRun
```

The application will be available at: http://localhost:8080

## Run tests

To run application test run:

```bash
./gradlew test
```

Test results will be available at: GitHubApi/build/reports/tests/test/index.html

## API Documentation

### Get Non-Fork Repositories

```bash
GET /api/github/{username}/repositories
```

Returns a list of non-fork public repositories with branch details.

Example request:

```bash
curl http://localhost:8080/api/github/AdrianRuchala/repositories
```

Response:

```bash
[
  {
    "repositoryName": "BookSaver",
    "ownerLogin": "AdrianRuchala",
    "branchesResponses": [
      {
        "name": "main",
        "lastCommitSha": "d0c24bb79268a84b85f75d8702da141a8da0ffaf"
      }
    ]
  },
  {
    "repositoryName": "ContactsApp",
    "ownerLogin": "AdrianRuchala",
    "branchesResponses": [
      {
        "name": "main",
        "lastCommitSha": "73991e219a5844efb2fd833df7064004f6275a97"
      }
    ]
  }
]
```

### If user does not exist

Returns 404 with a custom JSON error:

```bash
{
  "status": 404,
  "message": "GitHub user not found: {username}"
}
```