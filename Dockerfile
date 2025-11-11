# -------------------------
# Stage 1: Build WAR
# -------------------------
FROM eclipse-temurin:17-jdk AS builder
WORKDIR /app

COPY . .
RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests

# -------------------------
# Stage 2: Run WAR on Tomcat
# -------------------------
FROM tomcat:10.1-jdk17

RUN rm -rf /usr/local/tomcat/webapps/*

COPY --from=builder /app/target/*.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080
CMD ["catalina.sh", "run"]
