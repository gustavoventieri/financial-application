FROM sapmachine:21.0.11-ubuntu AS builder
WORKDIR /app
COPY . .
RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests

FROM sapmachine:21.0.11-jre-ubuntu AS runner
WORKDIR /app

RUN rm -f /etc/apt/sources.list.d/sapmachine.list \
    && apt-get update \
    && apt-get install -y --no-install-recommends ca-certificates curl \
    && apt-get install -y --only-upgrade libcap2 \
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/*

RUN groupadd -r appuser && useradd -r -g appuser appuser

COPY --from=builder /app/target/app.jar ./app.jar
RUN chown -R appuser:appuser /app

USER appuser
EXPOSE 31000

HEALTHCHECK --interval=30s --timeout=3s --retries=3 \
    CMD curl -f http://localhost:31000/actuator/health || exit 1

ENTRYPOINT ["java", "-jar", "app.jar"]