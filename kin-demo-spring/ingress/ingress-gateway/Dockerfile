FROM java:8
MAINTAINER hjq

ENV JAR_ROOT_PATH ingress-gateway

VOLUME /tmp
# 自动解压tar.gz
ADD target/ingress-gateway.tar.gz ./

ENTRYPOINT java -Dspring.config.location=$JAR_ROOT_PATH/config/ -cp .:$JAR_ROOT_PATH/lib/*:$JAR_ROOT_PATH/config/*:${CLASSPATH} org.kin.demo.spring.ingress.gateway.IngressGatewayApplication
EXPOSE 9888 9888