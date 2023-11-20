FROM openjdk:18-jdk

WORKDIR /app

COPY target/Pipy-*.jar /app/pipy.jar

CMD ["java", "-jar", "pipy.jar"]