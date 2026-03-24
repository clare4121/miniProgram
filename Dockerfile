ARG WORK_DIR=/app/
ARG JAR_NAME="target/minprogram-v0.0.1.jar"
FROM adoptopenjdk/openjdk11:alpine-slim AS release
ARG JAR_NAME
ARG WORK_DIR
ENV TZ=Asia/Shanghai
ADD ${JAR_NAME} ${WORK_DIR}

CMD ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app/minprogram-v0.0.1.jar"]


