package com.cp.jnitest.java.jnitestjavaapp.jni;

import java.nio.ByteBuffer;

public class EncoderJNI {
    public native static long new_FileHolder(byte[] byte_array);
    public native static void delete_FileHolder(long ptr);

    public native static long FileHolder_encoded_size(long ptr);
    public native static void FileHolder_write(long ptr, ByteBuffer byte_buffer);
}
