#include "com_cp_jnitest_java_jnitestjavaapp_jni_EncoderJNI.h"
#include <string>
#include "base64.h"

JNIEXPORT jlong JNICALL Java_com_cp_jnitest_java_jnitestjavaapp_jni_EncoderJNI_new_1FileHolder(JNIEnv* jenv, jclass jcls, jbyteArray byte_array) {
    jsize j_length = jenv->GetArrayLength(byte_array);
    jbyte* j_bytes = jenv->GetByteArrayElements(byte_array, NULL);

    cp::encode_holder* encode_holder = cp::base64_encode((char*)j_bytes, (size_t)j_length);

    jenv->ReleaseByteArrayElements(byte_array, j_bytes, JNI_ABORT);

    return (jlong)encode_holder;
}

JNIEXPORT void JNICALL Java_com_cp_jnitest_java_jnitestjavaapp_jni_EncoderJNI_delete_1FileHolder(JNIEnv* jenv, jclass jcls, jlong ptr) {
    cp::encode_holder* encode_holder = (cp::encode_holder*)ptr;

    cp::free_encode_holder(encode_holder);
}

JNIEXPORT jlong JNICALL Java_com_cp_jnitest_java_jnitestjavaapp_jni_EncoderJNI_FileHolder_1encoded_1size(JNIEnv* jenv, jclass jcls, jlong ptr) {
    cp::encode_holder* encode_holder = (cp::encode_holder*)ptr;

    return encode_holder->length;
}

JNIEXPORT void JNICALL Java_com_cp_jnitest_java_jnitestjavaapp_jni_EncoderJNI_FileHolder_1write(JNIEnv* jenv, jclass jcls, jlong ptr, jobject byte_buffer) {
    cp::encode_holder* encode_holder = (cp::encode_holder*)ptr;

    jbyteArray encoded_bytes_array = jenv->NewByteArray((jsize)encode_holder->length);

    jenv->SetByteArrayRegion(encoded_bytes_array, 0, jenv->GetArrayLength(encoded_bytes_array), (jbyte*)encode_holder->encoded_bytes);

    jbyte* buffer = (jbyte*)jenv->GetDirectBufferAddress(byte_buffer);
    jenv->GetByteArrayRegion(encoded_bytes_array, 0, jenv->GetArrayLength(encoded_bytes_array), buffer);

    jenv->DeleteLocalRef(encoded_bytes_array);
}