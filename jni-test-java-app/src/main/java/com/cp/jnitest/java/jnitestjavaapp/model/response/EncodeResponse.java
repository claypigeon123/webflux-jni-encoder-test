package com.cp.jnitest.java.jnitestjavaapp.model.response;

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

    @JsonProperty("encoded-bytes")
    private byte[] encodedBytes;
}
