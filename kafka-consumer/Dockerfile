FROM openjdk:8-jdk-alpine

# Make a directory
RUN mkdir -p /app
WORKDIR /app

ENV BOOTSTRAP_SERVER http://localhost:9092
ENV SCHEMA_REGISTRY_URL http://localhost:8081
ENV DEFAULT_TOPIC clj-demo-topic

# Copy only the target jar over
COPY target/uberjar/kafka-consumer-0.1.0-SNAPSHOT-standalone.jar kafka-consumer.jar

# Run the JAR
CMD java -jar kafka-consumer.jar $DEFAULT_TOPIC