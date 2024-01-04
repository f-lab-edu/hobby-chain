FROM openjdk:11

EXPOSE 8080

ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} hobby-chain.jar

ENTRYPOINT ["java","-jar","/hobby-chain.jar"]