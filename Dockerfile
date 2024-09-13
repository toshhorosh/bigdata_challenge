FROM maven:3.9.9-amazoncorretto-11-al2023 AS mvn_builder
WORKDIR /app
COPY kafka_app/ /app/
ARG IMAGE_VERSION
RUN mvn versions:set -DnewVersion=$IMAGE_VERSION
RUN mvn clean -e -B package

FROM amazoncorretto:11.0.24-alpine3.20
RUN apk update && apk add --no-cache libc6-compat
WORKDIR /app
ARG IMAGE_VERSION
COPY --from=mvn_builder /app/target/kafka_app-$IMAGE_VERSION-jar-with-dependencies.jar /app.jar
COPY jars/spark-sql-kafka-0-10_2.13-3.5.1.jar /extra.jar
ENV JAVA_OPTS=""
ENV ARGS=""
ENTRYPOINT java $JAVA_OPTS -cp /app.jar:/extra.jar org.toshhorosh.bigdata.challenge.Main $ARGS