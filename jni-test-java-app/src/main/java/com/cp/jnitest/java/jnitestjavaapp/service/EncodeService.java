package com.cp.jnitest.java.jnitestjavaapp.service;

import com.cp.jnitest.java.jnitestjavaapp.model.response.EncodeResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

@Service
public class EncodeService {

    public Mono<EncodeResponse> encode(MultipartFile file) {

    }
}
