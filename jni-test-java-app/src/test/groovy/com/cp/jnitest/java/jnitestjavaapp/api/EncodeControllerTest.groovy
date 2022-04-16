package com.cp.jnitest.java.jnitestjavaapp.api

import com.cp.jnitest.java.jnitestjavaapp.model.response.EncodeResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.core.io.ResourceLoader
import org.springframework.http.HttpStatus
import org.springframework.http.client.MultipartBodyBuilder
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.ExchangeStrategies
import spock.lang.Specification

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EncodeControllerTest extends Specification {

    // rest api paths
    private static final String PATH_ENCODE_NATIVE = "/api/v1/encode/native"
    private static final String PATH_ENCODE_JAVA = "/api/v1/encode/java"

    // request constant
    private static final String ENCODE_MULTIPART_FILE_KEY = "file"

    // test resources
    private static final String PLAIN_IMAGE = "classpath:zug.png"
    private static final String ENCODED_IMAGE = "classpath:zug.b64"

    // --- --- ---

    @Autowired
    ResourceLoader resourceLoader

    @LocalServerPort
    int port

    WebTestClient webClient

    void setup() {
        def strategies = ExchangeStrategies.builder()
            .codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(2 * 1024 * 1024))
            .build()

        webClient = WebTestClient.bindToServer()
            .baseUrl("http://localhost:$port")
            .exchangeStrategies(strategies)
            .build()
    }

    def "valid request - encoding #uri"() {
        given:
        def builder = new MultipartBodyBuilder()
        builder.part(ENCODE_MULTIPART_FILE_KEY, resourceLoader.getResource(PLAIN_IMAGE))
        def parts = builder.build()

        when:
        def result = webClient.post()
            .uri(uri)
            .body(BodyInserters.fromMultipartData(parts))
            .exchange()
            .returnResult(EncodeResponse.class)

        then:
        result.getStatus() == HttpStatus.OK
        new String(result.getResponseBody().blockFirst().getEncodedBytes()) == new String(resourceLoader.getResource(ENCODED_IMAGE).getInputStream().readAllBytes())

        where:
        uri                || _
        PATH_ENCODE_NATIVE || _
        PATH_ENCODE_JAVA   || _
    }

    def "invalid request - json body #uri"() {
        when:
        def result = webClient.post()
            .uri(uri)
            .bodyValue("{ \"something\": \"spicy\" }")
            .exchange()
            .returnResult(Void.class)

        then:
        result.getStatus() == HttpStatus.UNSUPPORTED_MEDIA_TYPE

        where:
        uri                || _
        PATH_ENCODE_NATIVE || _
        PATH_ENCODE_JAVA   || _
    }

    def "invalid request - not a file part #uri"() {
        given:
        def builder = new MultipartBodyBuilder()
        builder.part("file", "Well, this is actually a string")
        def parts = builder.build()

        when:
        def result = webClient.post()
            .uri(uri)
            .body(BodyInserters.fromMultipartData(parts))
            .exchange()
            .returnResult(Void.class)

        then:
        result.getStatus() == HttpStatus.BAD_REQUEST

        where:
        uri                || _
        PATH_ENCODE_NATIVE || _
        PATH_ENCODE_JAVA   || _
    }
}
