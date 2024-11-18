# Step 1: Use an official OpenJDK runtime as a parent image
FROM openjdk:21

# Step 2: Set the working directory in the container
WORKDIR /app

# Step 3: Copy the executable JAR from the target folder into the container
COPY target/demo-0.0.1-SNAPSHOT.jar app.jar

# Step 4: Expose the port that the Spring Boot app will run on
EXPOSE 8080

# Step 5: Run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]
