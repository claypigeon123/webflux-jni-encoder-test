package com.cp.jnitest.java.jnitestjavaapp.service

import com.cp.jnitest.java.jnitestjavaapp.util.encoder.Base64Encoder
import com.cp.jnitest.java.jnitestjavaapp.util.encoder.EncoderImplType
import com.cp.jnitest.java.jnitestjavaapp.util.encoder.impl.JavaBase64Encoder
import com.cp.jnitest.java.jnitestjavaapp.util.encoder.impl.NativeBase64Encoder
import org.springframework.core.io.ByteArrayResource
import org.springframework.core.io.buffer.DataBufferUtils
import org.springframework.core.io.buffer.DefaultDataBufferFactory
import org.springframework.http.codec.multipart.FilePart
import spock.lang.Specification

import java.util.stream.Collectors

class EncodeServiceTest extends Specification {

    EncodeService encodeService

    void setup() {
        Collection<Base64Encoder> encoders = new ArrayList<>()
        encoders.addAll(new NativeBase64Encoder(), new JavaBase64Encoder())
        encodeService = new EncodeService(encoders)
    }

    void "encoding #bytes returns #expected with all encoder types"() {
        given: "an uploaded file part"
        def part = Mock(FilePart)

        when: "EncodeService is called to encode the file part with the given method"
        def results = Arrays.stream(EncoderImplType.values())
            .map(type -> encodeService.encode(part, type).block())
            .collect(Collectors.toList())

        then: "the resulting base64 encoded bytes match the expected encoded bytes"
        _ * part.content() >> DataBufferUtils.read(new ByteArrayResource(bytes.getBytes()), new DefaultDataBufferFactory(), 1024)

        results.findAll {  new String(it.getEncodedBytes()) != expected }.isEmpty()

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
