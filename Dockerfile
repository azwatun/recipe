# Stage 1: Build the application
FROM gradle:jdk21 AS build
WORKDIR /app
COPY . /app
RUN gradle build --no-daemon

# Stage 2: Create the runtime image
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "app.jar"]
