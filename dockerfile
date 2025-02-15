# Use the official Maven image as the base image
FROM maven:3.8.4-openjdk-11-slim AS build

# Set the working directory in the container
WORKDIR /app

# Copy the project files into the container
COPY pom.xml .
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Use the official OpenJDK image as the base image for the runtime
FROM openjdk:11-jre-slim

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file from the build stage to the runtime image
COPY --from=build /app/target/loan_app.war .

# Expose the port that your Spring Boot application will run on
EXPOSE 8080

# Specify the command to run your application
ENTRYPOINT ["java", "-jar", "loan_app.war"]
