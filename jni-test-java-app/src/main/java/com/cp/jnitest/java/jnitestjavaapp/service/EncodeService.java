package com.cp.jnitest.java.jnitestjavaapp.service;

import com.cp.jnitest.java.jnitestjavaapp.model.exception.NoEncoderImplementationFoundException;
import com.cp.jnitest.java.jnitestjavaapp.model.response.EncodeResponse;
import com.cp.jnitest.java.jnitestjavaapp.util.encoder.Base64Encoder;
import com.cp.jnitest.java.jnitestjavaapp.util.encoder.EncoderImplType;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.*;

@Service
public class EncodeService {

    private final Map<EncoderImplType, Base64Encoder> encoderMap;

    public EncodeService(Collection<Base64Encoder> encoders) {
        encoderMap = new HashMap<>();
        encoders.forEach(encoder -> encoderMap.put(encoder.getType(), encoder));
    }

    public Mono<EncodeResponse> encode(FilePart file, EncoderImplType encoderImplType) {
        return file.content()
            .map(this::dataBufferToBytes)
            .reduce(this::combineBytes)
            .zipWith(Mono.defer(() -> encoderMap.containsKey(encoderImplType)
                ? Mono.just(encoderMap.get(encoderImplType))
                : Mono.error(new NoEncoderImplementationFoundException())
            ))
            .map(tuple2 -> tuple2.getT2().encode(tuple2.getT1()))
            .map(bytes -> new EncodeResponse(UUID.randomUUID().toString(), encoderImplType, bytes));
    }

    // --

    private byte[] dataBufferToBytes(DataBuffer dataBuffer) {
        byte[] bytes = new byte[dataBuffer.readableByteCount()];
        dataBuffer.read(bytes);
        return bytes;
    }

    private byte[] combineBytes(byte[] a, byte[] b) {
        byte[] combined = new byte[a.length + b.length];
        for (int i = 0; i < combined.length; i++) {
            combined[i] = i < a.length ? a[i] : b[i - a.length];
        }
        return combined;
    }
}
