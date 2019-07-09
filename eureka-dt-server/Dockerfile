FROM gmaslowski/jdk
VOLUME /tmp
ADD eureka-dt-server-0.0.1-SNAPSHOT.jar app_2.jar
#RUN bash -c 'touch /app_2.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app_2.jar"]
EXPOSE 8761