FROM amazoncorretto:21
WORKDIR /app
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8081
ENV SPRING_PROFILES_ACTIVE=local
ENTRYPOINT ["java", "-Xms700m","-Xmx700m", "-jar", "app.jar"]