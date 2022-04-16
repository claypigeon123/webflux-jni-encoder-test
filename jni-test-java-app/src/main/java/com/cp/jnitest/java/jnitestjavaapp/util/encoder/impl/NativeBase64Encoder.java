package com.cp.jnitest.java.jnitestjavaapp.util.encoder.impl;

import com.cp.jnitest.java.jnitestjavaapp.jni.FileHolder;
import com.cp.jnitest.java.jnitestjavaapp.util.WrappedByteBuffer;
import com.cp.jnitest.java.jnitestjavaapp.util.encoder.Base64Encoder;
import com.cp.jnitest.java.jnitestjavaapp.util.encoder.EncoderImplType;
import org.springframework.stereotype.Component;

import java.nio.ByteBuffer;

@Component
public class NativeBase64Encoder implements Base64Encoder {

    @Override
    public EncoderImplType getType() {
        return EncoderImplType.NATIVE;
    }

    @Override
    public byte[] encode(byte[] raw) {
        try (
            FileHolder fileHolder = new FileHolder(raw);
            WrappedByteBuffer wrapper = WrappedByteBuffer.direct((int) fileHolder.getEncodedSize())
        ) {
            ByteBuffer buffer = wrapper.buffer();
            fileHolder.write(buffer);

            buffer.rewind();
            byte[] encoded = new byte[buffer.remaining()];
            buffer.get(encoded);

            return encoded;
        }
    }

}
