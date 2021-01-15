FROM openjdk:8-jre-alpine
EXPOSE 8089
ADD target/alamisharia-testing-product-api.jar alamisharia-testing-product-api.jar

# MySQL
# ENTRYPOINT ["java","-Dspring.datasource.url=jdbc:mysql://host.docker.internal:3306/testing_product?serverTimezone=Asia/Jakarta", "-Duser.timezone=Asia/Jakarta", "-jar", "/alamisharia-testing-product-api.jar"]

# PostgreSQL
ENTRYPOINT ["java","-Dspring.datasource.url=jdbc:postgresql://host.docker.internal:25432/testing_product?currentSchema=testing_product", "-Duser.timezone=Asia/Jakarta", "-jar", "/alamisharia-testing-product-api.jar"]