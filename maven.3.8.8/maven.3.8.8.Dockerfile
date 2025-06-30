## <!-- Designed by Neftali Ramirez Chavez junio 2025 neftali.ramirez@plusnetwork.cloud -->
# FROM maven:3.3-jdk-8
# FROM maven:3.6.1-jdk-8-alpine

# Stage 1: BaseLine
# FROM maven:3-jdk-8-alpine
# FROM maven:3.8-jdk-11-alpine
FROM adnanahmady/maven:3.8.5-jdk-11-alpine
WORKDIR /
ENV MAVEN_HOME=/home/java/maven
ENV TELLER_HOME=/home/java/teller-x86_64-linux
COPY apache-maven-3.8.8 ${MAVEN_HOME}
COPY teller-x86_64-linux ${TELLER_HOME}

# ENV PATH=$PATH:$MAVEN_HOME/bin 
ENV GLIBC_VER=2.35-r1
RUN ln -sf $MAVEN_HOME/bin/mvn  /usr/local/bin/mvn \
    ln -sf $TELLER_HOME/teller  /usr/local/bin/teller \
    && echo 'export PATH=$MAVEN_HOME/bin/mvn:$TELLER_HOME/teller:~/.local/bin:$PATH' >> ~/.ashrc \
        && echo "========================================================" \
        && source ~/.ashrc \
set euo pipefail; \
apk --no-cache add curl; \
curl --silent --location https://alpine-pkgs.sgerrand.com/sgerrand.rsa.pub --output /etc/apk/keys/sgerrand.rsa.pub; \
curl --silent --location --remote-name https://github.com/sgerrand/alpine-pkg-glibc/releases/download/${GLIBC_VER}/glibc-${GLIBC_VER}.apk; \
curl --silent --location --remote-name https://github.com/sgerrand/alpine-pkg-glibc/releases/download/${GLIBC_VER}/glibc-bin-${GLIBC_VER}.apk; \
curl --silent --location --remote-name https://github.com/sgerrand/alpine-pkg-glibc/releases/download/${GLIBC_VER}/glibc-dev-${GLIBC_VER}.apk; \
curl --silent --location --remote-name https://github.com/sgerrand/alpine-pkg-glibc/releases/download/${GLIBC_VER}/glibc-i18n-${GLIBC_VER}.apk; \
apk --no-cache add \
    glibc-${GLIBC_VER}.apk \
    glibc-bin-${GLIBC_VER}.apk \
    glibc-dev-${GLIBC_VER}.apk \
    glibc-i18n-${GLIBC_VER}.apk; \
    # optional: add if needed, will add ~10mb to the final image
    /usr/glibc-compat/bin/localedef -i en_US -f UTF-8 en_US.UTF-8; \
    # replace symlink to point to glibc version instead of musl version
    ln -sf /usr/glibc-compat/lib/ld-linux-x86-64.so.2 /lib64/ld-linux-x86-64.so.2; \
    apk --no-cache del curl glibc-i18n; \
    rm -rf \
        /var/cache/apk/* \
        /etc/apk/keys/sgerrand.rsa.pub \
        glibc*${GLIBC_VER}.apk 
# =================================================================================================
## Version de AWS CLI V2 
# =================================================================================================
RUN apk --no-cache add groff less binutils bash curl \
    && curl -sL https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip -o awscliv2.zip \
    && unzip awscliv2.zip \
    && aws/install 
    # && rm -rf \
    # awscliv2.zip \
    # aws \
    # /usr/local/aws-cli/v2/*/dist/aws_completer \
    # /usr/local/aws-cli/v2/*/dist/awscli/data/ac.index \
    # /usr/local/aws-cli/v2/*/dist/awscli/examples \
    # && apk --no-cache del \
    # binutils \
    # curl \
RUN ln -sf /usr/local/aws-cli/v2/*/dist/aws /usr/local/bin/aws \
    && echo 'export PATH=/usr/local/bin/aws:~/.local/bin:$PATH' >> ~/.ashrc \
    && echo "========================================================" \
    && source ~/.ashrc 



        
COPY wait-for-it.sh /wait-for-it.sh
COPY start-maven.3.8.8.sh /start-maven.3.8.8.sh
RUN chmod +x /start-maven.3.8.8.sh
RUN chmod +x /wait-for-it.sh
ENTRYPOINT ["/wait-for-it.sh"]



# ARG MAVEN_VERSION=3.8.8
# ARG BASE_URL=https://apache.osuosl.org/maven/maven-3/3.8.8/binaries

# ENV MAVEN_CONFIG=/home/java/.m2
# ARG SHA=b4880fb7a3d81edd190a029440cdf17f308621af68475a4fe976296e71ff4a4b546dd6d8a58aaafba334d309cc11e638c52808a4b0e818fc0fd544226d952544

# RUN source ~/.bashrc
# RUN mvn -version

# ENV MAVEN_HOME=/usr/share/java/maven-3
# RUN apk del maven
# RUN apk --purge del maven 
# ENV MAVEN_HOME=/usr/share/java/maven-3
# RUN apk upgrade && apk add --no-cache bash maven>=3.8.6
# RUN apk upgrade
# COPY apache-maven-3.8.8 /usr/share/java/maven-3
# COPY pom.xml .

# COPY src ./src
# RUN mvn clean test

# # Stage 2: Build
# FROM maven:3.6.1-jdk-8-alpine as build
# WORKDIR /home/java/app/deployment/callingninja-api-user/
# COPY --from=dependencia spring-boot-starter-webclient-1.0.0-SNAPSHOT.jar .
# COPY pom.xml .
# COPY src ./src
# RUN mvn install:install-file -Dfile=spring-boot-starter-webclient-1.0.0-SNAPSHOT.jar \
# -DgroupId=cloud.plusnetwork.webclient \
# -DartifactId=spring-boot-starter-webclient \
# -Dversion=1.0.0-SNAPSHOT \
# -Dpackaging=jar \
# -DgeneratePom=true
# RUN mvn clean package -DskipTests

# FROM maven:3.6.1-jdk-8-alpine as production
# WORKDIR /home/java/app/deployment/callingninja-api-user/
# RUN apk add --no-cache bash
# RUN addgroup --system --gid 1001 cloud
# RUN adduser --system --uid 1001 java
# COPY --from=build --chown=java:cloud /home/java/app/deployment/callingninja-api-user/target/callingninja-api-user-1.0.0-SNAPSHOT.jar /home/java/app/deployment/callingninja-api-user/callingninja-api-user-1.0.0-SNAPSHOT.jar
# COPY --chown=java:cloud /src/main/resources/api.user.callingninja.com.mx.key /home/java/app/deployment/callingninja-api-user/api.user.callingninja.com.mx.key
# COPY --chown=java:cloud /src/main/resources/api.user.callingninja.com.mx.crt /home/java/app/deployment/callingninja-api-user/api.user.callingninja.com.mx.crt

# COPY start-api-user.sh /start-api-user.sh
# COPY wait-for-it.sh /wait-for-it.sh
# RUN chmod +x /start-api-user.sh
# RUN chmod +x /wait-for-it.sh

# EXPOSE 8081

# ENTRYPOINT ["/wait-for-it.sh"]