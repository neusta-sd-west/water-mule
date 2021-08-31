FROM maven:3.8-openjdk-11 AS build
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
RUN mvn -f /usr/src/app/pom.xml clean package

FROM openjdk:11
COPY --from=build /usr/src/app/target/mqtt-client-0.0.1-SNAPSHOT.jar watermule-mosqitto-client.jar
ENTRYPOINT ["java", "-jar", "/watermule-mosqitto-client.jar"]
