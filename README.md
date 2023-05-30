# Blend's Shop API
This repository shows a shop system example built in Spring Boot 3 + Java 17.

---

## What is used on this project?
The principal libraries/tools used are:
- [Maven](https://maven.apache.org/) for dependencies management
- [GraalVM](https://www.graalvm.org/) support for a faster environment
- [Localstack](https://localstack.cloud/) to simulate AWS S3 image upload
- [Docker](https://www.docker.com/) and [Docker Compose](https://docs.docker.com/compose/) to create portable environments
- [JUnit5](https://junit.org/junit5/) and [Mockito](https://site.mockito.org/) for unit testing
- [Spring Security](https://spring.io/projects/spring-security) to create dynamic access control and filter chains
- [Spring Data](https://spring.io/projects/spring-data) for database access
- [Spring Validation](https://www.baeldung.com/spring-boot-bean-validation) to validate the incoming data on controllers 
- [PostgreSQL](https://www.postgresql.org/) as database choosen to persist data
- [Insomnia](https://insomnia.rest/) as REST client to interact with API

## Why this project was created?
This API is part of a portfolio project. It was created to improve my skills with some libraries/tools and expand my knowledge 
about others.
Addicted to it, I built a front-end to consume this API and you can find it [on Github repository](https://github.com/duducharapa/blends-shop).

---

## Install
You can install the application using the **Docker Compose** tool. The compose file has these services:
- The API service called **api** in port 8080
- The web application service called **app** in port 3000
- The Localstack service called **localstack** in port 5466 
- The PostgreSQL service called **database** in port 5432

### Steps
#### 1. Start containers 
Just run the following command:

> docker-compose up -d

Then, the containers will build automatically

#### 2. Optional - Start Localstack bucket
Make sure you have the awslocal CLI installed if you wanna use the image upload functionality.

If you don't want, everything
is ready to use. Else, you need to run the ```postinstall.sh``` file to create the bucket on Localstack.

#### Gotcha!
After these steps, the application is ready to perform actions.

---

## How to use
You can use the web app to interact with API or use any REST client like Insomnia.

#### Insomnia
To use this client, just import the data on ```.insomnia``` directory to your application.

To import, go to **Preferences** > **Data** > **Import**. After import the file, just click on **Scan**.

#### Web app
Just open your browser and access the ```http://localhost:3000``` after start the application.
