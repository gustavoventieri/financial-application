# ==========================
# Stage 1 - Build
# ==========================
FROM maven:3.9.11-eclipse-temurin-21-alpine AS builder

WORKDIR /app

# Copia os POMs primeiro para aproveitar o cache
COPY pom.xml .
COPY core/pom.xml core/pom.xml
COPY framework/pom.xml framework/pom.xml

# Baixa as dependências
RUN mvn dependency:go-offline -B

# Copia o restante do projeto
COPY . .

# Compila apenas o módulo framework e suas dependências
RUN mvn clean package -pl framework -am -DskipTests

# ==========================
# Stage 2 - Runtime
# ==========================
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY --from=builder /app/framework/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]