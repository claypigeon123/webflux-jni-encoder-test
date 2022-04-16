package com.cp.jnitest.java.jnitestjavaapp.util.encoder;

public interface Base64Encoder {
    EncoderImplType getType();

    byte[] encode(byte[] raw);
}
