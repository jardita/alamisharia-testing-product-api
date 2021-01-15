FROM openjdk:8-jre-alpine
EXPOSE 8080
ADD target/alamisharia-rest-api.jar alamisharia-rest-api.jar
ENTRYPOINT ["java","-Dspring.datasource.url=jdbc:mysql://host.docker.internal:3306/alamisharia?serverTimezone=Asia/Jakarta", "-Duser.timezone=Asia/Jakarta", "-jar", "/alamisharia-rest-api.jar"]