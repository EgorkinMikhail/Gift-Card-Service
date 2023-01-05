FROM openjdk:17-jdk-slim
ADD target/gift-card-service-1.0.jar app.jar
EXPOSE 8090

ENTRYPOINT ["java", "-jar", "/app.jar"]