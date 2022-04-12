package com.cp.jnitest.java.jnitestjavaapp.api;

import com.cp.jnitest.java.jnitestjavaapp.model.response.EncodeResponse;
import com.cp.jnitest.java.jnitestjavaapp.service.EncodeService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/encode")
public class EncodeController {
    private static final Logger log = LoggerFactory.getLogger(EncodeController.class);

    private final EncodeService service;

    @PostMapping
    public Mono<EncodeResponse> encode(
        @RequestBody MultipartFile file
    ) {
        log.info("Received request to encode a new file: {}", file.getName());

        return service.encode(file);
    }
}
