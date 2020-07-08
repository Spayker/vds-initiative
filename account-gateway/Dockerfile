FROM openjdk:11-jre

ADD ./target/account-gateway.jar /app/
CMD ["java", "-Xmx400m", "-jar", "/app/account-gateway.jar"]

EXPOSE $ACCOUNT_GATEWAY_DEV_PORT $ACCOUNT_GATEWAY_DEV_DEBUG_PORT