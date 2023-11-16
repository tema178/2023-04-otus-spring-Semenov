FROM maven:3.8.5-openjdk-17-slim AS build

ENV PROJECT_DIR=/opt/project

RUN mkdir -p $PROJECT_DIR

ADD ./src $PROJECT_DIR/src
ADD ./pom.xml $PROJECT_DIR
RUN mvn -f $PROJECT_DIR clean package

#
# Package stage
#
FROM openjdk:17-slim
ENV PROJECT_DIR=/opt/project
RUN mkdir -p $PROJECT_DIR
WORKDIR $PROJECT_DIR
COPY --from=build $PROJECT_DIR/target/hw17-1.0-SNAPSHOT.jar $PROJECT_DIR/
RUN mkdir $PROJECT_DIR/logs; touch $PROJECT_DIR/logs/hw-17.log; ln -sf /dev/stdout $PROJECT_DIR/logs/hw-17.log
EXPOSE 8080
CMD ["java", "-jar", "/opt/project/hw17-1.0-SNAPSHOT.jar"]