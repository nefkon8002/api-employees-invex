## <!-- Designed by Neftali Ramirez Chavez junio 2025 neftali.ramirez@plusnetwork.cloud -->
# FROM maven:3.3-jdk-8
FROM maven:3.3-jdk-11
# Maintainer
MAINTAINER Neftali Ramirez Chavez <nefkon@gmail.com>
# Set environment variable for Maven version
ENV MVN_VERSION 3.6.3
WORKDIR /home/java/app/deployment/plusnetwork-api-employees/
COPY . ./
COPY start-api-employees.sh /start-api-employees.sh
COPY wait-for-it.sh /wait-for-it.sh
RUN chmod +x /start-api-employees.sh
RUN chmod +x /wait-for-it.sh

EXPOSE 8081

ENTRYPOINT ["/wait-for-it.sh"]