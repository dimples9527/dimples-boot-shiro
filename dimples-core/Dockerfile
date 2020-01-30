FROM openjdk:8

# 个人信息
MAINTAINER zhongyj "1126834403@qq.com"

# 指定临时文件的输出目录
VOLUME /dimples/logs/shiro/tmp

# 要添加到镜像中的文件
# docker拷贝文件不允许超过当前目录
ADD target/dimples-shiro-system-1.0.0.jar app.jar

RUN sh -c 'touch /app.jar'
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]