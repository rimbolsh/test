FROM openjdk:8-jre-slim
COPY build/libs/*.jar /usr/src/test/demoApp.jar
WORKDIR /usr/src/test
 
RUN ln -sf /usr/share/zoneinfo/Asia/Seoul /etc/localtime
 
EXPOSE 80
CMD ["java", "-jar" ,"demoApp.jar"]
