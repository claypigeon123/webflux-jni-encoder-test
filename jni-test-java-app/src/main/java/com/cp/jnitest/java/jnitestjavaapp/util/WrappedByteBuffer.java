package com.cp.jnitest.java.jnitestjavaapp.util;

import lombok.*;

import java.nio.ByteBuffer;

@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class WrappedByteBuffer implements AutoCloseable {
    private final ByteBuffer buffer;

    public static WrappedByteBuffer direct(int size) {
        return new WrappedByteBuffer(ByteBuffer.allocateDirect(size));
    }

    public static WrappedByteBuffer heap(int size) {
        return new WrappedByteBuffer(ByteBuffer.allocate(size));
    }

    public ByteBuffer buffer() {
        return buffer;
    }

    @Override
    public void close() {
        if (buffer != null) buffer.clear();
    }
}
