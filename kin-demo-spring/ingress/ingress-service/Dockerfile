FROM java:8
MAINTAINER hjq

ENV JAR_ROOT_PATH ingress-service

VOLUME /tmp
# 自动解压tar.gz
ADD target/ingress-service.tar.gz ./

ENTRYPOINT java -Dspring.config.location=$JAR_ROOT_PATH/config/ -cp .:$JAR_ROOT_PATH/lib/*:$JAR_ROOT_PATH/config/*:${CLASSPATH} org.kin.demo.spring.ingress.service.IngressServiceApplication
EXPOSE 10000 10000