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

class EncodeServiceTest extends Specification {

    EncodeService encodeService

    void setup() {
        Collection<Base64Encoder> encoders = new ArrayList<>()
        encoders.addAll(new NativeBase64Encoder(), new JavaBase64Encoder())
        encodeService = new EncodeService(encoders)
    }

    def "encode \"#bytes\" with #method method returns \"#expected\""() {
        given: "an uploaded file part"
        def part = Mock(FilePart)

        when: "EncodeService is called to encode the file part with the given method"
        def result = encodeService.encode(part, method).block()

        then: "the resulting base64 encoded bytes match the expected encoded bytes"
        1 * part.content() >> DataBufferUtils.read(new ByteArrayResource(bytes.getBytes()), new DefaultDataBufferFactory(), 1024)

        new String(result.getEncodedBytes()) == expected

        where:
        method                 | bytes    || expected
        EncoderImplType.NATIVE | "f"      || "Zg=="
        EncoderImplType.NATIVE | "fo"     || "Zm8="
        EncoderImplType.NATIVE | "foo"    || "Zm9v"
        EncoderImplType.NATIVE | "foob"   || "Zm9vYg=="
        EncoderImplType.NATIVE | "fooba"  || "Zm9vYmE="
        EncoderImplType.NATIVE | "foobar" || "Zm9vYmFy"
        EncoderImplType.JAVA   | "f"      || "Zg=="
        EncoderImplType.JAVA   | "fo"     || "Zm8="
        EncoderImplType.JAVA   | "foo"    || "Zm9v"
        EncoderImplType.JAVA   | "foob"   || "Zm9vYg=="
        EncoderImplType.JAVA   | "fooba"  || "Zm9vYmE="
        EncoderImplType.JAVA   | "foobar" || "Zm9vYmFy"
    }
}
