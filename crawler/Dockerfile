FROM amazoncorretto:21
WORKDIR /app

ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8082
ENV SPRING_PROFILES_ACTIVE=local

ENTRYPOINT ["java","-Xms256m", "-Xmx256m" ,"-jar", "app.jar"]

