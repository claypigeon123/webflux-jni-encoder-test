package com.cp.jnitest.java.jnitestjavaapp.model.response;

import com.cp.jnitest.java.jnitestjavaapp.util.encoder.EncoderImplType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EncodeResponse {

    @JsonProperty("request-id")
    private String requestId;

    @JsonProperty("encoded-with")
    private EncoderImplType encodedWith;

    @JsonProperty("encoded-bytes")
    private byte[] encodedBytes;
}
