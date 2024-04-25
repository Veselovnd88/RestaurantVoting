# Implementation for Restaurant Voting task

[TopJava Graduation Project](https://github.com/JavaWebinar/topjava/blob/doc/doc/graduation.md)

--------------------------
Design and implement a REST API using Spring-Boot/Spring Data JPA without frontend.

The task is:

Build a voting system for deciding where to have lunch.

2 types of users: admin and regular users
Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price) <br>
Menu changes each day (admins do the updates) <br>
Users can vote for a restaurant they want to have lunch at today <br>
Only one vote counted per user <br>
If user votes again the same day: <br>
If it is before 11:00 we assume that he changed his mind. <br>
If it is after 11:00 then it is too late, vote can't be change <br>
Each restaurant provides a new menu each day. <br>

As a result, provide a link to github repository. It should contain the code, README.md with API documentation and <br>
couple curl commands to test it (better - link to Swagger). <br>

P.S.: Make sure everything works with latest version that is on github :) <br> 
P.P.S.: Assume that your API will be used by a frontend developer to build frontend on top of that. <br> 

--------------
Swagger UI implemented for testing API.

Credentials for testing API:  localhost:8082/swagger-ui.html: <br>

| Login           | Password |
|-----------------|----------|
| admin@gmail.com | admin    |
| user@yandex.ru  | password |
| user2@gmail.com | another  |

--------------------------
Application stack:

- Java 21;
- Spring Boot 3.2.3;
- Spring Data Jpa;
- Spring Validation API;
- Spring MVC;
- Spring Cache (Caffeine);
- Spring Security;
- Lombok;
- Mapstruct;
- HSQLDB as Database;

193 Unit and Integration tests, coverage is 92% 
