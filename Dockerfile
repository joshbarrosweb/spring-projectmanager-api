FROM openjdk:11-jre-slim
VOLUME /tmp
COPY build/libs/projectmanager-0.0.1-SNAPSHOT.jar app.jar
COPY src/main/resources/db/migration /flyway/sql
CMD ["java", "-jar", "/app.jar"]
