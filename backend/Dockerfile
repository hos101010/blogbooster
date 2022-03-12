FROM adoptopenjdk:11-jdk-hotspot AS builder
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src
RUN chmod +X ./gradlew
RUN ./gradlew bootJar

FROM adoptopenjdk:11-jdk-hotspot
COPY --from=builder build/libs/*.jar blogbooster.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/blogbooster.jar"]
