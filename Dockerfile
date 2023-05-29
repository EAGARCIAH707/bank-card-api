FROM maven:3.8.3-openjdk-11 as builder

WORKDIR /app
COPY pom.xml .
COPY src ./src

RUN mvn clean install package -DskipTests

FROM openjdk:11-oracle

COPY --from=builder /app/target/card-api-*.jar /card-api.jar

CMD ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/card-api.jar"]

EXPOSE 8080
