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
