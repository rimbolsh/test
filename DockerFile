FROM openjdk:8-jre-slim
COPY build/libs/demo-0.0.1-SNAPSHOT.jar /usr/src/test/
WORKDIR /usr/src/test
 
RUN ln -sf /usr/share/zoneinfo/Asia/Seoul /etc/localtime
 
EXPOSE 80
CMD ["java", "-jar" ,"demo-0.0.1-SNAPSHOT.jar"]
