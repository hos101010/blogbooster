FROM adoptopenjdk:11-jdk-hotspot AS builder
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src
RUN chmod +X ./gradlew
RUN ./gradlew bootJar -x test

FROM adoptopenjdk:11-jdk-hotspot
COPY --from=builder build/libs/*.jar pearl.jar

EXPOSE 8080
EXPOSE 3306


ARG PROFILE="dev"
RUN echo "PROFILE is ${PROFILE}"
ENV PROFILE=${PROFILE}

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=${PROFILE}", "/pearl.jar"]