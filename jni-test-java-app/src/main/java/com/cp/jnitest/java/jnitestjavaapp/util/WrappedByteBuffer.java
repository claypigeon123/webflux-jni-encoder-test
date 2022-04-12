package com.cp.jnitest.java.jnitestjavaapp.util;

import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.nio.ByteBuffer;

@Data
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class WrappedByteBuffer implements AutoCloseable {
    private final ByteBuffer buffer;

    public static WrappedByteBuffer direct(int size) {
        return new WrappedByteBuffer(ByteBuffer.allocateDirect(size));
    }

    public static WrappedByteBuffer heap(int size) {
        return new WrappedByteBuffer(ByteBuffer.allocate(size));
    }

    @Override
    public void close() {
        if (buffer != null) buffer.clear();
    }
}
