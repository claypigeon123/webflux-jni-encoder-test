package com.cp.jnitest.java.jnitestjavaapp.service

import com.cp.jnitest.java.jnitestjavaapp.test.MockFilePart
import spock.lang.Specification

class EncodeServiceTest extends Specification {

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
