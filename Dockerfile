FROM maven:3.9.9-amazoncorretto-11-al2023 AS MVN_BUILDER
WORKDIR /app
COPY kafka_app/ /app/
ARG IMAGE_VERSION
RUN mvn -e -B dependency:resolve
RUN mvn versions:set -DnewVersion=$IMAGE_VERSION
RUN mvn clean -e -B package

FROM amazoncorretto:11.0.24-alpine3.20
WORKDIR /app
ARG IMAGE_VERSION
COPY --from=MVN_BUILDER /app/target/kafka_app-$IMAGE_VERSION-jar-with-dependencies.jar /app.jar
ENV JAVA_OPTS=""
ENV ARGS=""
ENTRYPOINT java $JAVA_OPTS -cp /app.jar org.toshhorosh.bigdata.challenge.Main $ARGS