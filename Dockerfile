ARG JAR_VERSION
FROM openjdk:8-jre-slim
COPY build/libs/demo-$JAR_VERSION.jar /usr/src/test/
WORKDIR /usr/src/test
 
RUN ln -sf /usr/share/zoneinfo/Asia/Seoul /etc/localtime
 
EXPOSE 80
CMD ["java", "-jar" ,"demo-{$JAR_VERSION}.jar"]
