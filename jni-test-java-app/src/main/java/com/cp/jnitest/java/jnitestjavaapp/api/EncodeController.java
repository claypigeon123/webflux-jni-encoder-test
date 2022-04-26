package com.cp.jnitest.java.jnitestjavaapp.api;

import com.cp.jnitest.java.jnitestjavaapp.model.exception.InvalidPartInRequestException;
import com.cp.jnitest.java.jnitestjavaapp.model.response.EncodeResponse;
import com.cp.jnitest.java.jnitestjavaapp.service.EncodeService;
import com.cp.jnitest.java.jnitestjavaapp.util.encoder.EncoderImplType;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.codec.multipart.Part;
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

    @PostMapping("/{encoderType}")
    public Mono<EncodeResponse> encodeNative(
        @RequestPart("file") Mono<Part> part,
        @PathVariable EncoderImplType encoderType
    ) {
        return part.flatMap(this::mapPartToFilePart)
            .doOnNext(filePart -> log.info("Received request to {} encode new file: {}", encoderType.getDisplayName(), filePart.filename()))
            .flatMap(filePart -> service.encode(filePart, encoderType));
    }

    // --

    private Mono<FilePart> mapPartToFilePart(Part part) {
        return part instanceof FilePart fp ? Mono.just(fp) : Mono.error(new InvalidPartInRequestException());
    }
}
