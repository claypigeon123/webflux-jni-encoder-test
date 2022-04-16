package com.cp.jnitest.java.jnitestjavaapp.util.encoder.impl;

import com.cp.jnitest.java.jnitestjavaapp.util.encoder.Base64Encoder;
import com.cp.jnitest.java.jnitestjavaapp.util.encoder.EncoderImplType;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class JavaBase64Encoder implements Base64Encoder {
    private static final Base64.Encoder ENCODER = Base64.getEncoder();

    @Override
    public EncoderImplType getType() {
        return EncoderImplType.JAVA;
    }

    @Override
    public byte[] encode(byte[] raw) {
        return ENCODER.encode(raw);
    }

}
