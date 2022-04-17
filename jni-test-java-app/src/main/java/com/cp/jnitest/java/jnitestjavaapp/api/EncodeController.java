package com.cp.jnitest.java.jnitestjavaapp.api;

import com.cp.jnitest.java.jnitestjavaapp.model.response.EncodeResponse;
import com.cp.jnitest.java.jnitestjavaapp.service.EncodeService;
import com.cp.jnitest.java.jnitestjavaapp.util.encoder.EncoderImplType;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/encode", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class EncodeController {
    private static final Logger log = LoggerFactory.getLogger(EncodeController.class);

    private final EncodeService service;

    // Special case for when "file" key in a multipart is NOT a file part
    @ExceptionHandler(ClassCastException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Mono<Void> handleClassCastException(ClassCastException e) {
        return Mono.empty();
    }
    // ------------------------------------------------------------------

    @PostMapping("/native")
    public Mono<EncodeResponse> encodeNative(@RequestPart Mono<FilePart> file) {
        return file
            .doOnNext(filePart -> log.info("Received request to native encode new file: {}", filePart.filename()))
            .flatMap(filePart -> service.encode(filePart, EncoderImplType.NATIVE));
    }

    @PostMapping("/java")
    public Mono<EncodeResponse> encodeJava(@RequestPart Mono<FilePart> file) {
        return file
            .doOnNext(filePart -> log.info("Received request to java encode new file: {}", filePart.filename()))
            .flatMap(filePart -> service.encode(filePart, EncoderImplType.JAVA));
    }
}
