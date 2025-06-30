Para poder arrancar el proyecto se require tener instalado Docker y Docker Compose 
Se recomienda verificar que no se encuentre corriendo ningun contenedor 
Docker ps 

ejecutar el shell docker-build-baseImage.sh el cual se encarga de crear el registry donde se depositara la imagen base del proyecto 

se encarga de construir la imagen con la version de java 11 compila y arranca el proyecto. este incluye una parte observavilidad para poder monitorear los logs con elastic search , kibana y demas componentes.

Cabe mansionar que la API Employee tiene implementado el mecanismo de authenticacion BASIC AUTHENTICATION 
https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/basic.html

Se aplica la practica devsecops los secrets se manejan con la herramienta Teller para no exponer claves sensibles 
https://github.com/tellerops/teller



https://localhost:8082/swagger-ui/index.html#/employee-resource/readEmployeeByGuid
./docker-build-baseImage.sh

En video se muestra todo el proceso de arranque.