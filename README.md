# poster

Poster is a simple and user-friendly application built using a microservices architecture in order to achieve better durability and independence between functionalities. It allows its users to create their own profile, share a post, leave a comment under a post, and like it. There are two different searches - post and user searches. Every registered user has got the ability to manage his own profile and check his last-day activity. The main entry point of the system is the member gateway on port 8080.

**Framework**:
Spring Boot

**Programming language**:
Java 17

**Additional libraries and tools:**
Gradle, Docker, Kafka, PostgreSQL, JWT, Jackson, Swagger, Hibernate, Flyway, Lombok, Mapstruct

FUll list of APIs and functionalities:
- **POST api/v1/users** - register a new user;
- **POST api/v1/auth/login** - log in the platform. It will re-activate the user too and will show his posts to the community;
- **PATCH api/v1/users/password** - change user's password;
- **PATCH api/v1/users/{username}/deactivate** - deactivate user's profile and all his posts. This will make the user's account invisible to the other people;
- **GET api/v1/users/{username}** - get user's account;
- **GET api/v1/users/{username}/profile** - shows the full profile of the user that includes a full information plus user's posts;
- **POST api/v1/users/search** - search users by username and apply different sort filters and directions;
- **DELETE api/v1/users/{username}** - delete user' profile and all posts;
- **POST api/v1/posts** - create a new post and increase the user visible and total post counts;
- **PUT api/v1/posts** - update a specific post;
- **PATCH api/v1/posts/display** - hide a specific post and change the user visible post count;
- **GET api/v1/posts/{postId}** - open a specific post and increase the view count;
- **GET api/v1/posts/{postId}/comments** - show all the post comment;
- **GET api/v1/posts/{postId}/likes** - show all the post likes;
- **POST api/v1/posts/search** - search posts by author, title and rating. Apply different sort and direction filters;
- **DELETE api/v1/posts/{postId}** - delete a specific post and update the user visible and total post counts;
- **POST api/v1/likes** - like a post, increase the post like count and save this action to the history;
- **DELETE api/v1/likes/{likeId}** - unlike a specific post, decrease the post like count and save this action to the history;
- **POST api/v1/comments** - leave a comment, increase the post comment count and save this action to the history;
- **PUT api/v1/comments** - update your own comment;
- **DELETE api/v1/comments/{commentId}** - delete your own comment, decrease the post comment count and save this action to the history;
- **GET api/v1/history/{username}** - load specific type of history.

**Swagger URL**:
http://localhost:8080/swagger-ui/index.html#

What steps you need to follow if you want to run the application:
- Download and install **Java 17 SDK or higher** on your personal computer;
- Download and install an IDE - **IntellijIDEA, Eclipse etc**;
- Download and install **Docker**;
- Import the project using **Gradle**;
- Open the terminal and go to the directory where you saved the application and execute this command: **docker-compose up -d**
- The docker container will run a zookeper on port **2181**, a kafka broker on port **9092** and a postgres server with predefined databases on port **5432**;
- Lastly, run all the microservices: 
  - member-gateway on port **8080**;
  - authenticaion-service on port **8081**;
  - history-service on port **8082**;
  - post-service on port **8083**;
  - user-service on port **8084**.
