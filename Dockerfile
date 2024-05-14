# Start with a base image containing Java runtime
FROM amazoncorretto:21

# Make port 8080 available to the world outside this container
EXPOSE 3000

# The application's jar file
ARG JAR_FILE=target/webpos-0.0.1-SNAPSHOT.jar

# Add the application's jar to the container
ADD ${JAR_FILE} app.jar

# Run the jar file
ENTRYPOINT ["java","-jar","/app.jar"]
