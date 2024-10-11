#It uses an Ubuntu-based image for the first layer of the container
FROM ubuntu:latest AS build

#Ubuntu update
RUN apt-get update

#Install openjdk-17-jdk , -y makes it automatically
RUN apt-get install openjdk-17-jdk -y

#Copy the library to Docker
COPY . .

#Pack the aplication to a runnable .jar file
RUN ./mvnw package

#Slim version, optimaliziert, with not so much extra package
FROM openjdk:17-jdk-slim

#Port
EXPOSE 8080

#Copy the .jar file and put it into the container as app.jar
COPY --from=build /target/PriorIT_sb_books_mvc-1.jar app.jar

#Running the Java aplication 
ENTRYPOINT ["java", "-jar", "app.jar"]
