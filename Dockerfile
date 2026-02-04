# Build stage
FROM gradle:8.14.4-jdk17 AS build
WORKDIR /app

# Copy gradle files
COPY build.gradle settings.gradle gradlew ./
COPY gradle gradle/

# Copy source code
COPY src src/

# Build application
RUN ./gradlew clean build -x test --no-daemon

# Runtime stage
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# Copy jar from build stage
COPY --from=build /app/build/libs/*.jar app.jar

# Expose port
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=40s --retries=3 \
  CMD curl -f http://localhost:8080/health || exit 1

# Run application
ENTRYPOINT ["java", "-jar", "app.jar"]
