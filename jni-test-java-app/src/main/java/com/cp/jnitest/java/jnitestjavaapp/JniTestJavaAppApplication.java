package com.cp.jnitest.java.jnitestjavaapp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JniTestJavaAppApplication implements CommandLineRunner {
    private static final String ENCODER_LIBRARY_NAME = "jni-test-native";

    public static void main(String[] args) {
        SpringApplication.run(JniTestJavaAppApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.loadLibrary(ENCODER_LIBRARY_NAME);
        //System.load("G:\\side-hustles\\jni-test\\lib\\jni-test-native.dll");
    }
}
