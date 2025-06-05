# Etap build (Java 21 + Maven)
FROM maven:3.9.6-eclipse-temurin-21 as builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Etap runtime (Java 21 JDK)
FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=builder /app/target/wodozbior-*.jar app.jar
EXPOSE 6969
ENTRYPOINT ["java", "-jar", "app.jar"]
