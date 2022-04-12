#include "EncoderJNI.h"

JNIEXPORT jlong JNICALL Java_com_cp_jnitest_java_jnitestjavaapp_jni_EncoderJNI_new_1FileHolder(JNIEnv* jenv, jclass jcls, jbyteArray byte_array) {
	jbyte* raw_bytes = jenv->GetByteArrayElements(byte_array, NULL);
	jsize size = jenv->GetArrayLength(byte_array);

	std::string raw_bytes_string = "";

	jbyte* raw_bytes_iterator = raw_bytes;
	for (int i = 0; i < size; i++) {
		const unsigned char* value_ptr = (const unsigned char*) raw_bytes_iterator;
		raw_bytes_string.append((const char*) *value_ptr);
		raw_bytes_iterator++;
	}

	jenv->ReleaseByteArrayElements(byte_array, raw_bytes, 0);

	std::string encoded_string = base64.Encode(raw_bytes_string);
	char* encoded_bytes_start_ptr = new char[encoded_string.length() + 1];
	std::copy(encoded_string.begin(), encoded_string.end(), encoded_bytes_start_ptr);

	FileHolder file_holder = { encoded_bytes_start_ptr, encoded_string.length() + 1 };

	return (jlong) &file_holder;
}

JNIEXPORT void JNICALL Java_com_cp_jnitest_java_jnitestjavaapp_jni_EncoderJNI_delete_1FileHolder(JNIEnv* jenv, jclass jcls, jlong ptr) {
	FileHolder* file_holder = (FileHolder*) ptr;

	delete file_holder->encoded_bytes;
	delete file_holder;
}

JNIEXPORT jlong JNICALL Java_com_cp_jnitest_java_jnitestjavaapp_jni_EncoderJNI_FileHolder_1encoded_1size(JNIEnv* jenv, jclass jcls, jlong ptr) {
	FileHolder* file_holder = (FileHolder*)ptr;

	return file_holder->length;
}

JNIEXPORT void JNICALL Java_com_cp_jnitest_java_jnitestjavaapp_jni_EncoderJNI_FileHolder_1write(JNIEnv* jenv, jclass jcls, jlong ptr, jobject byte_buffer) {
	FileHolder* file_holder = (FileHolder*)ptr;

	jbyte* encoded_bytes = (jbyte*) file_holder->encoded_bytes;
	jbyteArray encoded_bytes_array = jenv->NewByteArray((jsize)file_holder->length);

	jenv->SetByteArrayRegion(encoded_bytes_array, 0, jenv->GetArrayLength(encoded_bytes_array), encoded_bytes);

	jbyte* buffer = (jbyte*) jenv->GetDirectBufferAddress(byte_buffer);
	jenv->GetByteArrayRegion(encoded_bytes_array, 0, jenv->GetArrayLength(encoded_bytes_array), buffer);

	jenv->DeleteLocalRef(encoded_bytes_array);
}