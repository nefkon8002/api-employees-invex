## <!-- Designed by Neftali Ramirez Chavez junio 2025 neftali.ramirez@plusnetwork.cloud -->

cd /home/java/app/deployment/plusnetwork-api-employees/
## Development
# mvn clean spring-boot:run 
## Produccion
# java -jar callingninja-api-user-1.0.0-SNAPSHOT.jar
# echo $CLASSPATH 
ls -ltr /home/java/app/deployment/plusnetwork-api-employees/

# java \
# -cp /home/java/app/deployment/callingninja-api-user/spring-boot-starter-webclient-1.0.0-SNAPSHOT.jar \
# -jar /home/java/app/deployment/callingninja-api-user/callingninja-api-user-1.0.0-SNAPSHOT.jar

# java \
# -cp /home/java/app/deployment/callingninja-api-user/callingninja-api-user-1.0.0-SNAPSHOT.jar \
# -Dloader.path=/home/java/app/deployment/callingninja-api-user/spring-boot-starter-webclient-1.0.0-SNAPSHOT.jar org.springframework.boot.loader.PropertiesLauncher

java org.springframework.boot.loader.JarLauncher

# It should work now, even though the main class in manifest 
# of fat_app.jar is set to 'org.springframework.boot.loader.JarLauncher'
# and your additional classes will be picked up by springboot