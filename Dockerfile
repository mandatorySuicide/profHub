# Use the Amazon Corretto 17 base image
FROM amazoncorretto:17

# Set the working directory
WORKDIR /app

# Copy the JAR file to the working directory
COPY build/libs/profhunter-1.0.jar ./profhunter-1.0.jar

# Define environment variables with placeholders
ENV EMAIL=TO_BE_DEFINED
ENV PASSWORD=TO_BE_DEFINED
ENV URL=TO_BE_DEFINED

# Command to run the application
CMD ["sh", "-c", "java -jar /app/profhunter-1.0.jar -e=${EMAIL} -p=${PASSWORD} -u=${URL}"]