FROM openjdk:21-jdk-slim

# 設定時區為 Asia/Taipei
RUN apt-get update && \
    apt-get install -y tzdata && \
    ln -fs /usr/share/zoneinfo/Asia/Taipei /etc/localtime && \
    dpkg-reconfigure -f noninteractive tzdata

COPY target/springboot-modular-monolith.jar .

EXPOSE 8080

ENTRYPOINT ["java","-jar","springboot-modular-monolith.jar"]