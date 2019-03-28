FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
ADD config-server-0.0.1-SNAPSHOT.jar app_1.jar
#RUN bash -c 'touch /app_1.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app_1.jar"]
EXPOSE 8767