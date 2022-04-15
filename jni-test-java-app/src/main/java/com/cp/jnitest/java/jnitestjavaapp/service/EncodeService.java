package com.cp.jnitest.java.jnitestjavaapp.service;

import com.cp.jnitest.java.jnitestjavaapp.jni.FileHolder;
import com.cp.jnitest.java.jnitestjavaapp.model.response.EncodeResponse;
import com.cp.jnitest.java.jnitestjavaapp.util.WrappedByteBuffer;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.UUID;
import java.util.function.BiFunction;

@Service
public class EncodeService {

    public Mono<EncodeResponse> encodeNative(FilePart file) {
        return file.content()
            .map(this::mapToBytes)
            .reduce(byteArrayReducer)
            .map(this::encodeBytesNative)
            .map(bytes -> new EncodeResponse(UUID.randomUUID().toString(), bytes));
    }

    public Mono<EncodeResponse> encodeJava(FilePart file) {
        return file.content()
            .map(this::mapToBytes)
            .reduce(byteArrayReducer)
            .map(this::encodeBytesJava)
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

    private byte[] encodeBytesNative(byte[] rawBytes) {
        try (
            FileHolder fileHolder = new FileHolder(rawBytes);
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

    private byte[] encodeBytesJava(byte[] rawBytes) {
        return Base64.getEncoder().encode(rawBytes);
    }
}
