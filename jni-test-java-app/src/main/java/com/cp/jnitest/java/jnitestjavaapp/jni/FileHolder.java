package com.cp.jnitest.java.jnitestjavaapp.jni;

import java.nio.ByteBuffer;

public class FileHolder implements AutoCloseable {
    private long cPtr;

    public FileHolder(byte[] bytes) {
        this.cPtr = EncoderJNI.new_FileHolder(bytes);
    }

    public long getEncodedSize() {
        return EncoderJNI.FileHolder_encoded_size(cPtr);
    }

    public void write(ByteBuffer buffer) {
        if (buffer != null && !buffer.isReadOnly() && buffer.isDirect()) {
            EncoderJNI.FileHolder_write(cPtr, buffer);
        }
    }

    @Override
    public void close() throws Exception {
        if (cPtr != 0) {
            EncoderJNI.delete_FileHolder(cPtr);
            cPtr = 0;
        }
    }
}
