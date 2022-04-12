# Webflux JNI Encoder Test
A simple Spring Webflux application & custom c++ to play with JNI.

## jni-test-java-app

The app exposes two endpoints:
- `POST` `/api/v1/encode/native`
  - Takes form-data as request body, looks for a file part with the key `file`
  - Uses native c++ to BASE64 encode the given file
  - Returns json with `request-id` and `encoded-bytes` fields
- `POST` `/api/v1/encode/java`
  - Takes form-data as request body, looks for a file part with the key `file`
  - Uses java facilities to BASE64 encode the given file
  - Returns json with `request-id` and `encoded-bytes` fields

## jni-test-native

The c++ side of things. Builds to a .dll file which is loaded by the Spring app.
