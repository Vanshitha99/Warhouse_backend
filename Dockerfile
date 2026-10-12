# ===================================================================
# Multi-stage Dockerfile for Spring Boot Backend on Render
# Stage 1: Build JAR using Maven and Java 26 JDK
# Stage 2: Execute using slim Java 26 JRE
# ===================================================================

# Stage 1: Builder
FROM eclipse-temurin:26-jdk AS builder
WORKDIR /workspace

# Copy Maven wrapper and POM to leverage layer caching
COPY mvnw pom.xml ./
COPY .mvn .mvn
RUN chmod +x ./mvnw

# Pre-fetch dependencies
RUN ./mvnw dependency:go-offline -B || true

# Copy source and build executable JAR
COPY src ./src
RUN ./mvnw clean package -DskipTests -B

# Stage 2: Runtime
FROM eclipse-temurin:26-jre
WORKDIR /app

# Run as unprivileged user for security
RUN groupadd -r spring && useradd -r -g spring spring
USER spring:spring

# Copy compiled JAR from builder stage
COPY --from=builder /workspace/target/*.jar app.jar

# Dynamic PORT assigned by Render
ENV PORT=8080
EXPOSE ${PORT}

# Container-friendly memory flags and default production profile
ENTRYPOINT ["sh", "-c", "java -XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0 -Dserver.port=${PORT} -Dspring.profiles.active=${SPRING_PROFILES_ACTIVE:-prod} -jar app.jar"]
