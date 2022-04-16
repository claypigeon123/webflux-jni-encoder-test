package com.cp.jnitest.java.jnitestjavaapp.service;

import com.cp.jnitest.java.jnitestjavaapp.model.response.EncodeResponse;
import com.cp.jnitest.java.jnitestjavaapp.util.encoder.Base64Encoder;
import com.cp.jnitest.java.jnitestjavaapp.util.encoder.EncoderImplType;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiFunction;

@Service
public class EncodeService {

    private final Map<EncoderImplType, Base64Encoder> encoderMap;

    public EncodeService(Collection<Base64Encoder> encoders) {
        encoderMap = new HashMap<>();
        encoders.forEach(encoder -> encoderMap.put(encoder.getType(), encoder));
    }

    public Mono<EncodeResponse> encode(FilePart file, EncoderImplType encoderImplType) {
        return file.content()
            .map(this::mapToBytes)
            .reduce(byteArrayReducer)
            .map(raw -> encoderMap.get(encoderImplType).encode(raw))
            .map(bytes -> new EncodeResponse(UUID.randomUUID().toString(), bytes));
    }

    // --

    private byte[] mapToBytes(DataBuffer dataBuffer) {
        byte[] bytes = new byte[dataBuffer.readableByteCount()];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = dataBuffer.read();
        }
        return bytes;
    }

    private final BiFunction<byte[], byte[], byte[]> byteArrayReducer = (a, b) -> {
        byte[] combined = new byte[a.length + b.length];
        for (int i = 0; i < combined.length; i++) {
            combined[i] = i < a.length ? a[i] : b[i - a.length];
        }
        return combined;
    };
}
