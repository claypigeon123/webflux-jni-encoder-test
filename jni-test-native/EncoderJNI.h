#include <jni.h>
#include <string>
#include "Base64.h"

#ifndef _Included_com_cp_jnitest_java_jnitestjavaapp_jni_EncoderJNI
#define _Included_com_cp_jnitest_java_jnitestjavaapp_jni_EncoderJNI
#ifdef __cplusplus

macaron::Base64 base64;

struct FileHolder {
	char* encoded_bytes;
	long length;
};

extern "C" {
#endif
	JNIEXPORT jlong JNICALL Java_com_cp_jnitest_java_jnitestjavaapp_jni_EncoderJNI_new_1FileHolder
	(JNIEnv*, jclass, jbyteArray);

	JNIEXPORT void JNICALL Java_com_cp_jnitest_java_jnitestjavaapp_jni_EncoderJNI_delete_1FileHolder
	(JNIEnv*, jclass, jlong);

	JNIEXPORT jlong JNICALL Java_com_cp_jnitest_java_jnitestjavaapp_jni_EncoderJNI_FileHolder_1encoded_1size
	(JNIEnv*, jclass, jlong);

	JNIEXPORT void JNICALL Java_com_cp_jnitest_java_jnitestjavaapp_jni_EncoderJNI_FileHolder_1write
	(JNIEnv*, jclass, jlong, jobject);

#ifdef __cplusplus
}
#endif
#endif
