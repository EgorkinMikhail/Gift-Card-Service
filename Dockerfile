FROM adoptopenjdk/openjdk19:jre-19.0.1
VOLUME /tmp
EXPOSE 8090:8091
EXPOSE 8091:8091
EXPOSE 8070:8070
ADD /target/gift-card-service-1.0.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar", "--spring.config.location=file:/application.yaml"]