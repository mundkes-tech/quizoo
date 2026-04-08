# Quizoo - Online Quiz System

Quizoo is a Spring Boot web application for creating and attempting quizzes with automatic evaluation.

It supports role-based access for ADMIN and USER, quiz management, scoring, result tracking, and attempt restriction.

## Features

### Authentication and Authorization
- Login and registration
- Role-based access control (`ROLE_ADMIN`, `ROLE_USER`)

### Admin Features
- Create quizzes with dynamic questions and options
- Edit quizzes
- Delete quizzes
- View all quiz results

### User Features
- View available quizzes
- Attempt quiz
- Submit answers
- View personal results
- See attempted quizzes marked in quiz list

### System Features
- Automatic score calculation
- Prevent multiple attempts per user per quiz
- JSP-based UI with shared CSS styling

## Tech Stack
- Java 17
- Spring Boot 4.0.5
- Spring Security
- Spring Data JPA
- JSP + JSTL + Tomcat Jasper
- MySQL
- Maven

## Project Structure

```text
src/main/java/com/mundke/quizoo
├── config
│   └── SecurityConfig.java
├── controller
│   ├── AuthController.java
│   ├── HomeController.java
│   ├── PageController.java
│   ├── QuizController.java
│   └── ResultController.java
├── dto
│   ├── LoginDTO.java
│   ├── QuizAttemptDTO.java
│   ├── QuizDTO.java
│   └── ResultDTO.java
├── model
│   ├── Option.java
│   ├── Question.java
│   ├── Quiz.java
│   ├── Result.java
│   └── User.java
├── repository
│   ├── QuestionRepository.java
│   ├── QuizRepository.java
│   ├── ResultRepository.java
│   └── UserRepository.java
├── service
│   ├── AuthService.java
│   ├── QuizService.java
│   ├── ResultService.java
│   └── impl
│       ├── AuthServiceImpl.java
│       ├── QuizServiceImpl.java
│       └── ResultServiceImpl.java
└── QuizooApplication.java

src/main/webapp/WEB-INF/views
├── admin-quiz-form.jsp
├── admin-quizzes.jsp
├── attempt.jsp
├── home.jsp
├── index.jsp
├── login.jsp
├── quiz-list.jsp
├── register.jsp
├── result.jsp
└── results.jsp

src/main/resources/static/css
└── style.css
```

## Database Schema

Logical schema used by the application:

- users (`app_user`)
  - id
  - username
  - password
  - role
- quizzes (`quiz`)
  - id
  - title
  - description
  - created_at
- questions (`quiz_question`)
  - id
  - quiz_id
  - question_text
- options (`quiz_option`)
  - id
  - question_id
  - option_text
  - is_correct
- results (`quiz_result`)
  - id
  - user_id
  - quiz_id
  - score
  - attempted_at

## Getting Started

### 1. Prerequisites
- Java 17+
- Maven 3.9+
- MySQL 8+

### 2. Clone

```bash
git clone <your-repo-url>
cd quizoo
```

### 3. Configure MySQL
Create database:

```sql
CREATE DATABASE quizapp;
```

Update datasource values in `src/main/resources/application.properties` if needed.

### 4. Run Application

```bash
mvn spring-boot:run
```

Application runs at:

- `http://localhost:8080`

## Default Seeded Credentials

These users are auto-created at startup if missing:

- Admin
  - Username: `admin`
  - Password: `admin123`
- User
  - Username: `user`
  - Password: `user123`

## Main UI Routes

- Public
  - `GET /`
  - `GET /login`
  - `GET /register`
- User
  - `GET /home`
  - `GET /quizzes`
  - `GET /quiz/{id}`
  - `POST /submit`
  - `GET /results`
- Admin
  - `GET /admin/quizzes`
  - `GET /admin/quizzes/new`
  - `GET /admin/quizzes/edit/{id}`
  - `POST /admin/quizzes/save`
  - `POST /admin/quizzes/delete/{id}`
  - `GET /admin/results`

## REST API Endpoints

### Auth
- `POST /api/auth/register`

Example body:

```json
{
  "username": "newuser",
  "password": "password123"
}
```

### Quiz APIs
- `POST /api/quizzes` (ADMIN)
- `GET /api/quizzes` (ADMIN/USER)
- `GET /api/quizzes/{id}` (ADMIN/USER)
- `PUT /api/quizzes/{id}` (ADMIN)
- `DELETE /api/quizzes/{id}` (ADMIN)

### Result APIs
- `POST /api/results/submit` (ADMIN/USER)
- `GET /api/results/me` (ADMIN/USER)
- `GET /api/results` (ADMIN/USER)
- `GET /api/results/quiz/{quizId}` (ADMIN/USER)

Submit example:

```json
{
  "userId": 2,
  "quizId": 1,
  "answers": {
    "1": 3,
    "2": 6
  }
}
```

## Security Notes

- CSRF is currently disabled for easier development/testing.
- Passwords are stored with BCrypt.
- Access rules are configured in `SecurityConfig`.

## Future Improvements

- Add quiz timer support
- Add pagination/search for quizzes/results
- Add validation and global exception handling
- Add unit and integration tests
- Add Docker support

---

If you find this project useful, consider starring the repository.
