FROM ghcr.io/graalvm/jdk:ol9-java17-22.3.2 AS build
WORKDIR /app
COPY . .
RUN ./mvnw clean install

FROM ghcr.io/graalvm/jdk:ol9-java17-22.3.2
WORKDIR /app
COPY --from=build /app/target/blends-shop-0.0.1-SNAPSHOT.jar app.jar
CMD ["java", "-jar", "app.jar"]