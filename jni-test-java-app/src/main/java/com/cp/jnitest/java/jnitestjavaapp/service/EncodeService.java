package com.cp.jnitest.java.jnitestjavaapp.service;

import com.cp.jnitest.java.jnitestjavaapp.jni.FileHolder;
import com.cp.jnitest.java.jnitestjavaapp.model.response.EncodeResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Scanner;
import java.util.UUID;
import java.util.function.BiFunction;

@Service
public class EncodeService {
    private static final Logger log = LoggerFactory.getLogger(EncodeService.class);

    public Mono<EncodeResponse> encode(FilePart file) {
        return file.content()
            .map(this::mapToBytes)
            .reduce(byteArrayReducer)
            .map(this::encode)
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

    private byte[] encode(byte[] rawBytes) {
        try (FileHolder fileHolder = new FileHolder(rawBytes)) {
            int size = (int) fileHolder.getEncodedSize();
            ByteBuffer buffer = ByteBuffer.allocateDirect(size);

            fileHolder.write(buffer);

            buffer.rewind();
            byte[] encoded = new byte[buffer.remaining()];
            buffer.get(encoded);

            return encoded;
        }
    }
}
