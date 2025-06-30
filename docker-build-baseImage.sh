#!/usr/bin/env bash
echo "//////////////////////////Building Base Image\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\"
cd ./maven.3.8.8/
docker run -d -p 5000:5000 --name registry registry:2.7
# ls -ltr 
# echo "Base Directory Image"
# docker build  --progress=plain . 2>&1 | tee build.log
# BASELINE_IMAGE_NAME=registry.plusnetwork.cloud:5000/maven-3.8-jdk-8-alpine:v1.0.0.SNAPSHOT
BASELINE_IMAGE_NAME=registry.plusnetwork.cloud:5000/maven-3.8.5-jdk-11-alpine:v1.0.0.SNAPSHOT
DOCKER_BUILDKIT=0 docker build --no-cache -f maven.3.8.8.Dockerfile -t $BASELINE_IMAGE_NAME .
docker push $BASELINE_IMAGE_NAME
cd ..
echo "//////////////////////////Building DOCKER COMPOSE DOWN\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\"

../../teller-x86_64-linux/v1.5.6/teller run -c .teller.bkp.yml -- docker compose -f docker-compose-dev-cloud.yml down --rmi local --remove-orphans
# docker compose -f docker-compose-dev-cloud.yml down --rmi local --remove-orphans

echo "////////////////////## <!-- Designed by Neftali Ramirez Chavez Junio 2023 nefkon80@gmail.com -->//////Building DOCKER COMPOSE UP\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\"

 ../../teller-x86_64-linux/v1.5.6/teller run -c .teller.bkp.yml -- docker compose -f docker-compose-dev-cloud.yml up --force-recreate
# docker compose -f docker-compose-dev-cloud.yml up --force-recreate

