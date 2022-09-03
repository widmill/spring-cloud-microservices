FROM adoptopenjdk/openjdk11:jre-11.0.6_10-alpine
EXPOSE 8989
COPY build/libs/gateway-1.0-SNAPSHOT.jar .
RUN apk add --no-cache bash
COPY wait-for.sh /wait-for.sh
RUN chmod +x /wait-for.sh
ENTRYPOINT ["bash", "wait-for.sh", "registry:8761", "--timeout=70", "--", "java", "-jar", "gateway-1.0-SNAPSHOT.jar"]