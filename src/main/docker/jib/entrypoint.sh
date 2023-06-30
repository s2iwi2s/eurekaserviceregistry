#!/bin/sh
#echo "eureka.client.service-url.defaultZone=${eureka.client.service-url.defaultZone}"
echo "SPRING_PROFILES_ACTIVE=${SPRING_PROFILES_ACTIVE}"
echo "SPRING_DATASOURCE_URL=${SPRING_DATASOURCE_URL}"
echo "JAVA_OPTS=${JAVA_OPTS}"
echo "APP_DELAY_STARTUP=${APP_DELAY_STARTUP}"
echo "The application will start in ${APP_DELAY_STARTUP}s..." && sleep ${APP_DELAY_STARTUP}
exec java ${JAVA_OPTS} -noverify -XX:+AlwaysPreTouch -Djava.security.egd=file:/dev/./urandom -cp /app/resources/:/app/classes/:/app/libs/* "com.stoi.fightcluckservice.FightcluckserviceApplication"  "$@"
