package com.cp.jnitest.java.jnitestjavaapp.service

import lombok.RequiredArgsConstructor
import org.springframework.core.io.ByteArrayResource
import org.springframework.core.io.buffer.DataBuffer
import org.springframework.core.io.buffer.DataBufferFactory
import org.springframework.core.io.buffer.DataBufferUtils
import org.springframework.core.io.buffer.DefaultDataBufferFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.codec.multipart.FilePart
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import spock.lang.Specification

import java.nio.file.Path

class EncodeServiceTest extends Specification {

    private static class MockFilePart implements FilePart {
        private static final DataBufferFactory DATA_BUFFER_FACTORY = new DefaultDataBufferFactory()
        private final byte[] content

        MockFilePart(byte[] content) { this.content = content }

        @Override
        String filename() { return "mock-value" }

        @Override
        Mono<Void> transferTo(Path dest) { return Mono.empty() }

        @Override
        String name() { return "mock-key" }

        @Override
        HttpHeaders headers() { return HttpHeaders.EMPTY }

        @Override
        Flux<DataBuffer> content() {
            return DataBufferUtils.read(new ByteArrayResource(content), DATA_BUFFER_FACTORY, 1024)
        }
    }

    EncodeService encodeService

    void setup() {
        encodeService = new EncodeService()
    }

    def "encode natively"() {
        given:
        def part = new MockFilePart(bytes.getBytes())

        when:
        def result = encodeService.encodeNative(part).block()

        then:
        new String(result.getEncodedBytes()) == expected

        where:
        bytes    || expected
        "f"      || "Zg=="
        "fo"     || "Zm8="
        "foo"    || "Zm9v"
        "foob"   || "Zm9vYg=="
        "fooba"  || "Zm9vYmE="
        "foobar" || "Zm9vYmFy"
    }

    def "encode with java"() {
        given:
        def part = new MockFilePart(bytes.getBytes())

        when:
        def result = encodeService.encodeJava(part).block()

        then:
        new String(result.getEncodedBytes()) == expected

        where:
        bytes    || expected
        "f"      || "Zg=="
        "fo"     || "Zm8="
        "foo"    || "Zm9v"
        "foob"   || "Zm9vYg=="
        "fooba"  || "Zm9vYmE="
        "foobar" || "Zm9vYmFy"
    }
}
