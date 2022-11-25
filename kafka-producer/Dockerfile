FROM openjdk:8-jdk-alpine

# Make a directory
RUN mkdir -p /app
WORKDIR /app

ENV PORT 3000
ENV BOOTSTRAP_SERVER http://localhost:9092
ENV SCHEMA_REGISTRY_URL http://localhost:8081
ENV DEFAULT_TOPIC clj-demo-topic

# Copy only the target jar over
COPY target/uberjar/kafka-producer-0.1.0-SNAPSHOT-standalone.jar kafka-producer.jar

# Open the port
EXPOSE 3000

# Run the JAR
CMD java -jar kafka-producer.jar