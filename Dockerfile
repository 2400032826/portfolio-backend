FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY . .

RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests

EXPOSE 10000

CMD ["java","-Dserver.port=8081","-jar","target/portfolio-backend-0.0.1-SNAPSHOT.jar"]