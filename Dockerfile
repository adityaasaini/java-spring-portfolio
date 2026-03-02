# Step 1: Build stage
FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

# Step 2: Run stage
FROM eclipse-temurin:17-jdk-focal
# Yahan hum check kar rahe hain ki file ka naam kuch bhi ho, wo demo.jar ban jaye
COPY --from=build /target/*.war demo.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","demo.jar"]