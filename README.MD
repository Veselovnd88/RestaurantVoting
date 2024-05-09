[![](https://img.shields.io/badge/Spring%20Boot%20Version-3.2.3-orange)](/pom.xml) [![](https://img.shields.io/badge/Java%20Version-21-orange)](/pom.xml)
<img alt="GitHub Actions Workflow Status" src="https://img.shields.io/github/actions/workflow/status/Veselovnd88/RestaurantVoting/build-test-flow.yml?label=build-and-test">
[![Codacy Badge](https://app.codacy.com/project/badge/Grade/4d2c93f026ed46748330273246ca079c)](https://app.codacy.com/gh/Veselovnd88/RestaurantVoting/dashboard?utm_source=gh&utm_medium=referral&utm_content=&utm_campaign=Badge_grade)

# Implementation for Restaurant Voting task

[TopJava Graduation Project](https://github.com/JavaWebinar/topjava/blob/doc/doc/graduation.md)

--------------------------
Design and implement a REST API using Spring-Boot/Spring Data JPA without frontend.

The task is:

Build a voting system for deciding where to have lunch.

2 types of users: admin and regular users
Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price)  
Menu changes each day (admins do the updates)  
Users can vote for a restaurant they want to have lunch at today  
Only one vote counted per user  
If user votes again the same day:  
If it is before 11:00 we assume that he changed his mind.  
If it is after 11:00 then it is too late, vote can't be change  
Each restaurant provides a new menu each day.

As a result, provide a link to github repository. It should contain the code, README.md with API documentation and  
couple curl commands to test it (better - link to Swagger).

P.S.: Make sure everything works with latest version that is on github :)  
P.P.S.: Assume that your API will be used by a frontend developer to build frontend on top of that.

--------------
Run: `mvn spring-boot:run` to start app  
Application will be launched on http://localhost:8082, and can be tested with Swagger API

[Swagger UI implemented for testing API](http://localhost:8082/swagger-ui.html)

Credentials for testing API:

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
- Maven;

166 Unit and Integration tests, coverage is 93% 
