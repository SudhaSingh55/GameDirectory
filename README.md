# Game Directory API

API is a Spring Boot application that provides functionality for managing gamers and their favorite games.

The project is structured as follows:

- `src/main/java/org/game`: Contains the Java source code for the application.
  - `controller`: Controllers responsible for handling HTTP requests.
  - `entity`: Entity classes representing data objects.
  - `exception`: Custom exception classes.
  - `request`: Request classes for API endpoints.
  - `response`: Response classes for API endpoints.
  - `service`: Service classes for business logic.
  - `repository`: respositories classes for jpa interaction with db.
  - `Application.java`: The main application class.

- `src/main/resources`: Contains h2 schema and application configurations.

- `src/test/java/org/game`: Contains unit tests for the application.
    - `service`: Service test classes.

- `pom.xml`: Maven configuration file for managing project dependencies and build settings.

## Information

- Java Development Kit (JDK) 17
- Maven (for building and managing dependencies)
- This project has inbuilt h2 database for db interactions
- H2 console url : http://localhost:8080/h2-console/
- Swagger url : http://localhost:8080/swagger-ui/

Below are generic Basic url information,

- POST : gamers/create-user : Create/register user for gaming application.
- POST : gamers/create-game : Add games to database.
- POST : gamers/enroll-gamer : Enroll user into game using level of interest (noob, pro, invincible).
- GET : gamers/search-game : Search gamers based on (user, game and geography) but here either user/game name is mandatory.
- POST : gamers/credit-game : API to give credit to user based on game.
- GET : gamers/search-max-credit : This API gives max credits of gamer based on games.

