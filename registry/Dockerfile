FROM adoptopenjdk/openjdk11:jre-11.0.6_10-alpine
EXPOSE 8761
COPY build/libs/registry-1.0-SNAPSHOT.jar .
RUN apk add --no-cache bash
COPY wait-for.sh /wait-for.sh
RUN chmod +x /wait-for.sh
ENTRYPOINT ["bash", "wait-for.sh", "config-service:8001", "--timeout=70", "--","java", "-jar", "registry-1.0-SNAPSHOT.jar"]
