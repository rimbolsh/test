FROM openjdk:8-jre-slim
COPY build/libs/*.jar /usr/src/test/demoApp.jar
WORKDIR /usr/src/test
 
RUN ln -sf /usr/share/zoneinfo/Asia/Seoul /etc/localtime

ENV	USE_PROFILE local

ENTRYPOINT ["java", "-Dspring.profiles.active=${USE_PROFILE}", "-jar", "/demoApp.jar"]
