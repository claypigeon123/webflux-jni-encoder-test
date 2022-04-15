#include "com_cp_jnitest_java_jnitestjavaapp_jni_EncoderJNI.h"
#include <string>
#define CBASE64_IMPLEMENTATION
#include "cbase64.h"

namespace cp {
	struct file_holder {
		char* encoded_bytes;
		size_t length;
	};
}

JNIEXPORT jlong JNICALL Java_com_cp_jnitest_java_jnitestjavaapp_jni_EncoderJNI_new_1FileHolder(JNIEnv* jenv, jclass jcls, jbyteArray byte_array) {
	jsize j_length = jenv->GetArrayLength(byte_array);
	jbyte* j_bytes = jenv->GetByteArrayElements(byte_array, NULL);

	const unsigned int encoded_length = cbase64_calc_encoded_length(j_length + 1);
	char* encoded_out = (char*)malloc(encoded_length);
	char* encoded_out_end = encoded_out;

	cbase64_encodestate encode_state;
	cbase64_init_encodestate(&encode_state);
	encoded_out_end += cbase64_encode_block((unsigned char*)j_bytes,(uint64_t) j_length + 1, encoded_out_end, &encode_state);
	encoded_out_end += cbase64_encode_blockend(encoded_out_end, &encode_state);

	jenv->ReleaseByteArrayElements(byte_array, j_bytes, JNI_ABORT);

	size_t final_length = (encoded_out_end - encoded_out);

	cp::file_holder* file_holder = new cp::file_holder();
	file_holder->encoded_bytes = encoded_out;
	file_holder->length = final_length;

	return (jlong) file_holder;
}

JNIEXPORT void JNICALL Java_com_cp_jnitest_java_jnitestjavaapp_jni_EncoderJNI_delete_1FileHolder(JNIEnv* jenv, jclass jcls, jlong ptr) {
	cp::file_holder* file_holder = (cp::file_holder*) ptr;

	free(file_holder->encoded_bytes);
	delete file_holder;
}

JNIEXPORT jlong JNICALL Java_com_cp_jnitest_java_jnitestjavaapp_jni_EncoderJNI_FileHolder_1encoded_1size(JNIEnv* jenv, jclass jcls, jlong ptr) {
	cp::file_holder* file_holder = (cp::file_holder*)ptr;

	return file_holder->length;
}

JNIEXPORT void JNICALL Java_com_cp_jnitest_java_jnitestjavaapp_jni_EncoderJNI_FileHolder_1write(JNIEnv* jenv, jclass jcls, jlong ptr, jobject byte_buffer) {
	cp::file_holder* file_holder = (cp::file_holder*)ptr;

	jbyteArray encoded_bytes_array = jenv->NewByteArray((jsize)file_holder->length);

	jenv->SetByteArrayRegion(encoded_bytes_array, 0, jenv->GetArrayLength(encoded_bytes_array), (jbyte*)file_holder->encoded_bytes);

	jbyte* buffer = (jbyte*) jenv->GetDirectBufferAddress(byte_buffer);
	jenv->GetByteArrayRegion(encoded_bytes_array, 0, jenv->GetArrayLength(encoded_bytes_array), buffer);

	jenv->DeleteLocalRef(encoded_bytes_array);
}