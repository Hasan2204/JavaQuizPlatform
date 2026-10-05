# Java-Based Online Quiz Platform

Full-stack college project:
- Frontend: HTML, CSS, Vanilla JavaScript
- Backend: Java 21, Spring Boot, REST APIs
- Database: MySQL 8
- Data access: JDBC
- Password hashing: BCrypt

Roles:
1. Admin - users, quiz approval, settings, dashboard/statistics
2. Quiz Creator - create quizzes, review results, participant interaction
3. Participant - timed quizzes, reports, messages, reminders, leaderboard

## Run
1. Create the database using `database/schema.sql`.
2. Set the DB_PASSWORD environment variable to your local MySQL password.
3. From the project folder run:
   `mvn spring-boot:run`
4. Open:
   `http://localhost:8080/`


