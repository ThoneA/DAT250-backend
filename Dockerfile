FROM gradle:9.1.0-jdk21 AS build

WORKDIR /app 
# Denne ovenfor må kanskje endres på

COPY build.gradle.kts settings.gradle.kts ./
COPY gradle ./gradle
COPY gradlew ./gradlew
COPY gradlew.bat ./gradlew.bat

RUN ./gradlew dependencies --no-daemon || return 0

# COPY gradle gradle

# RUN gradle dependencies --no-daemon || return 0

COPY src ./src

RUN ./gradlew bootJar --no-daemon

# FROM eclipse-temurin:17-jre-alpine
FROM eclipse-temurin:21-jdk AS runtime

WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

RUN useradd -m springuser
USER springuser

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]