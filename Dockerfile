FROM openjdk:21
COPY target/chillers*.jar chillers.jar
ENTRYPOINT ["java", "-jar", "/chillers.jar", "--spring.profiles.active=prod"]