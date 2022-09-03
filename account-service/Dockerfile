FROM adoptopenjdk/openjdk11:jre-11.0.6_10-alpine
EXPOSE 8081
COPY build/libs/account-service-1.0-SNAPSHOT.jar .
RUN apk add --no-cache bash
COPY wait-for.sh /wait-for.sh
RUN chmod +x /wait-for.sh
ENTRYPOINT ["bash", "wait-for.sh", "gateway:8989", "--timeout=70", "--", "java", "-jar", "account-service-1.0-SNAPSHOT.jar"]