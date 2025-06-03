FROM openjdk:17
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} receipt-service.jar
ENTRYPOINT ["java", "-jar", "/receipt-service.jar"]