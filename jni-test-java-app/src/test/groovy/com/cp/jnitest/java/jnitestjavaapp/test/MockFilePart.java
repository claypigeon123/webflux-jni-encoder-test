package com.cp.jnitest.java.jnitestjavaapp.test;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.file.Path;

@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class MockFilePart implements FilePart {
    private static final DataBufferFactory DATA_BUFFER_FACTORY = new DefaultDataBufferFactory();

    private final byte[] content;

    @Override
    public String filename() {
        return "mock-value";
    }

    @Override
    public Mono<Void> transferTo(Path dest) {
        return Mono.empty();
    }

    @Override
    public String name() {
        return "mock-key";
    }

    @Override
    public HttpHeaders headers() {
        return HttpHeaders.EMPTY;
    }

    @Override
    public Flux<DataBuffer> content() {
        return DataBufferUtils.read(new ByteArrayResource(content), DATA_BUFFER_FACTORY, 1024);
    }
}
