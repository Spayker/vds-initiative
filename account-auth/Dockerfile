FROM openjdk:11-jre

ADD ./target/account-auth.jar /app/
CMD ["java", "-Xmx400m", "-jar", "/app/account-auth.jar"]

EXPOSE $ACCOUNT_AUTH_DEV_PORT $ACCOUNT_AUTH_DEV_DEBUG_PORT