FROM amazoncorretto:17
WORKDIR /app
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

EXPOSE 5500
ENV SPRING_PROFILES_ACTIVE=local
ENTRYPOINT ["java","-Xms250m", "-Xmx250m", "-jar", "app.jar"]