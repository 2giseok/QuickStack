FROM amazoncorretto:21
WORKDIR /app

ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8080
ENV SPRING_PROFILES_ACTIVE=local

ENTRYPOINT ["java","-Xms128m", "-Xmx128m" ,"-jar", "app.jar"]

