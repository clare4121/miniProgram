ARG WORK_DIR=/app/
ARG JAR_NAME="/target/minprogram-v0.0.1.jar"
FROM adoptium:17-jre AS release
ARG JAR_NAME
ARG WORK_DIR
ENV TZ=Asia/Shanghai
ADD ${JAR_NAME} ${WORK_DIR}
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
EXPOSE 8000

