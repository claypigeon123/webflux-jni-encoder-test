jni-test-native: com_cp_jnitest_java_jnitestjavaapp_jni_EncoderJNI.cpp check-env
	g++ -fPIC -shared \
	-I"${JAVA_HOME}/include/" \
	-I"${JAVA_HOME}/include/linux/" \
	-I"./" \
	-o ../lib/libjni-test-native.so com_cp_jnitest_java_jnitestjavaapp_jni_EncoderJNI.cpp

check-env:
ifndef JAVA_HOME
	$(error JAVA_HOME is not set)
endif

clean:
	rm jni-test-native
