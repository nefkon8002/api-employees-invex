## <!-- Designed by Neftali Ramirez Chavez junio 2025 neftali.ramirez@plusnetwork.cloud -->

# FROM registry.plusnetwork.cloud:5000/maven-3.8-jdk-8-alpine:v1.0.0.SNAPSHOT AS build
FROM registry.plusnetwork.cloud:5000/maven-3.8.5-jdk-11-alpine:v1.0.0.SNAPSHOT AS build
WORKDIR /home/java/app/deployment/plusnetwork-api-employees/
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests
RUN java -Djarmode=layertools -jar target/plusnetwork-api-employees-1.0.0-SNAPSHOT.jar extract


FROM registry.plusnetwork.cloud:5000/maven-3.8.5-jdk-11-alpine:v1.0.0.SNAPSHOT AS production
# FROM maven:3.8.8-ibmjava-8 AS production 
WORKDIR /home/java/app/deployment/plusnetwork-api-employees/
RUN apk add --no-cache bash
RUN addgroup --system --gid 1001 cloud
RUN adduser --system --uid 1001 java
COPY --from=build /home/java/app/deployment/plusnetwork-api-employees/dependencies/ ./
COPY --from=build /home/java/app/deployment/plusnetwork-api-employees/spring-boot-loader ./
COPY --from=build /home/java/app/deployment/plusnetwork-api-employees/snapshot-dependencies/ ./
COPY --from=build /home/java/app/deployment/plusnetwork-api-employees/application/ ./

# COPY --from=build --chown=java:cloud /home/java/app/deployment/callingninja-api-user/target/callingninja-api-user-1.0.0-SNAPSHOT.jar /home/java/app/deployment/callingninja-api-user/callingninja-api-user-1.0.0-SNAPSHOT.jar
# COPY --from=build --chown=java:cloud /home/java/app/deployment/callingninja-api-user/spring-boot-starter-webclient-1.0.0-SNAPSHOT.jar /home/java/app/deployment/callingninja-api-user/spring-boot-starter-webclient-1.0.0-SNAPSHOT.jar
COPY --chown=java:cloud /src/main/resources/api.user.callingninja.com.mx.key /home/java/app/deployment/plusnetwork-api-employees/api.user.callingninja.com.mx.key
COPY --chown=java:cloud /src/main/resources/api.user.callingninja.com.mx.crt /home/java/app/deployment/plusnetwork-api-employees/api.user.callingninja.com.mx.crt
# ENV CLASSPATH=/home/java/app/deployment/callingninja-api-user/spring-boot-starter-webclient-1.0.0-SNAPSHOT.jar
# RUN echo $CLASSPATH
# COPY . ./
COPY start-api-employee.sh /start-api-employee.sh
COPY wait-for-it.sh /wait-for-it.sh
RUN chmod +x /start-api-employee.sh
RUN chmod +x /wait-for-it.sh

EXPOSE 8081

ENTRYPOINT ["/wait-for-it.sh"]
# ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]