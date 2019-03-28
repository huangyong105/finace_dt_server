FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
ADD sleuth-server-0.0.1-SNAPSHOT.jar app_4.jar
#RUN bash -c 'touch /app_4.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app_4.jar"]
EXPOSE 9411