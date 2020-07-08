FROM openjdk:11-jre

ADD ./target/config.jar /app/
CMD ["java", "-Xmx400m", "-jar", "/app/config.jar"]

EXPOSE $CONFIG_SERVICE_DEV_PORT $CONFIG_SERVICE_DEV_DEBUG_PORT