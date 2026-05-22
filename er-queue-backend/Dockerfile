FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY . .

RUN chmod +x mvnw

RUN mvn clean package -DskipTests

EXPOSE 8080

CMD ["java", "-jar", "target/smart-hospital-management-system-0.0.1-SNAPSHOT.jar"]
