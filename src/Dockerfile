# ---- Build Stage ----
    FROM gradle:8.5-jdk17 AS build
    WORKDIR /app
    COPY --chown=gradle:gradle . .
    RUN gradle clean build --no-daemon
    
    # ---- Run Stage ----
    FROM eclipse-temurin:17-jdk-alpine
    WORKDIR /app
    COPY --from=build /app/build/libs/*.war app.war
    
    # You can use Spring Boot's built-in launcher for WARs
    EXPOSE 8080
    ENTRYPOINT ["java", "-jar", "app.war"]