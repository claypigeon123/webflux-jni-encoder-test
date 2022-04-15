FROM openjdk:17-jdk-slim AS setup

RUN mkdir -p /opt/lib-build
WORKDIR /opt/lib-build

COPY jni-test-native/base64.h base64.h
COPY jni-test-native/com_cp_jnitest_java_jnitestjavaapp_jni_EncoderJNI.h com_cp_jnitest_java_jnitestjavaapp_jni_EncoderJNI.h
COPY jni-test-native/com_cp_jnitest_java_jnitestjavaapp_jni_EncoderJNI.cpp com_cp_jnitest_java_jnitestjavaapp_jni_EncoderJNI.cpp
COPY jni-test-native/Makefile Makefile
RUN mkdir -p /opt/lib
RUN apt update && apt install make g++ -y
RUN make

FROM openjdk:17-jdk-slim

RUN mkdir -p /opt/microservice
WORKDIR /opt/microservice

EXPOSE 9000

ARG MICROSERVICE_NAME
ENV MICROSERVICE_NAME ${MICROSERVICE_NAME}

ARG MICROSERVICE_VERSION
ENV MICROSERVICE_VERSION ${MICROSERVICE_VERSION}

COPY jni-test-java-app/target/${MICROSERVICE_NAME}-${MICROSERVICE_VERSION}.jar ${MICROSERVICE_NAME}.jar
COPY --from=setup /opt/lib /opt/microservice/lib/

ENTRYPOINT exec java -jar -Djava.library.path=/opt/microservice/lib ${MICROSERVICE_NAME}.jar