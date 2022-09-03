FROM adoptopenjdk/openjdk11:jre-11.0.6_10-alpine
COPY build/libs/notification-service-1.0-SNAPSHOT.jar .
RUN apk add --no-cache bash
COPY wait-for.sh /wait-for.sh
RUN chmod +x /wait-for.sh
ENTRYPOINT ["bash", "wait-for.sh", "bill-service:8082", "--timeout=110", "--", "java", "-jar", "notification-service-1.0-SNAPSHOT.jar"]