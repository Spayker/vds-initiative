FROM openjdk:11-jre

ADD ./target/trainer-service.jar /app/
CMD ["java", "-Xmx400m", "-jar", "/app/trainer-service.jar"]

EXPOSE $TRAINER_SERVICE_DEV_PORT $TRAINER_SERVICE_DEV_DEBUG_PORT