package com.cp.jnitest.java.jnitestjavaapp.jni;

import java.nio.ByteBuffer;

public class EncoderJNI {
    private static final String ENCODER_LIBRARY_NAME = "jni-test-encoder";

    static {
        System.load(ENCODER_LIBRARY_NAME);
    }

    public native static long new_FileHolder(byte[] raw_bytes);
    public native static void delete_FileHolder(long cPtr);

    public native static void FileHolder_encode(long cPtr);
    public native static long FileHolder_encoded_size(long cPtr);
    public native static void FileHolder_write(long cPtr, ByteBuffer buffer);
}
