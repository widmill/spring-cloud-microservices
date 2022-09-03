FROM adoptopenjdk/openjdk11:jre-11.0.6_10-alpine
EXPOSE 8001
COPY build/libs/config-service-1.0-SNAPSHOT.jar .
ENTRYPOINT ["java", "-jar", "config-service-1.0-SNAPSHOT.jar"]