FROM ghcr.io/graalvm/jdk:ol9-java17-22.3.2
WORKDIR /app
COPY . .
RUN ./mvnw clean install
EXPOSE 8080
CMD ["java", "-jar", "target/blends-shop-0.0.1-SNAPSHOT.jar"]