FROM openjdk:17-jdk-alpine
#FROM adoptopenjdk/openjdk11:alpine-jre
#FROM openjdk:17-jdk

VOLUME /tmp

# Expose port
EXPOSE 8080

# Copy JAR file to container
COPY target/chatgptapi-0.0.1-SNAPSHOT.jar app.jar
RUN chmod -R 777  app.jar

# Set environment variables for database connection
#ENV DB_URL jdbc:mysql://mysql:3306/chatgptdb
#ENV DB_USERNAME root
#ENV DB_PASSWORD password



# Start the app
#ENTRYPOINT ["java","-jar","/app.jar"]
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]

