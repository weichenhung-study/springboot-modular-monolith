FROM openjdk:21-jdk

COPY target/springboot-modular-monolith.jar .

EXPOSE 8080

ENTRYPOINT ["java","-jar","springboot-modular-monolith.jar"]