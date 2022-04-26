package com.cp.jnitest.java.jnitestjavaapp.config;

import com.cp.jnitest.java.jnitestjavaapp.util.encoder.EncoderImplType;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

import java.io.IOException;

@Configuration
public class GeneralConfiguration {

    @Bean
    public Module encoderImplTypeJsonModule() {
        SimpleModule module = new SimpleModule();

        module.addSerializer(EncoderImplType.class, new JsonSerializer<>() {
            @Override
            public void serialize(EncoderImplType value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                gen.writeString(value.getDisplayName());
            }
        });

        module.addDeserializer(EncoderImplType.class, new JsonDeserializer<>() {
            @Override
            public EncoderImplType deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
                return EncoderImplType.fromDisplayName(p.getValueAsString());
            }
        });

        return module;
    }

    @Bean
    public Converter<String, EncoderImplType> encoderImplTypePathVariableDeserializer() {
        return new Converter<>() {
            @Override
            public EncoderImplType convert(String source) {
                return EncoderImplType.fromDisplayName(source);
            }
        };
    }
}
