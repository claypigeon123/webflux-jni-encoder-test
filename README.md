# Webflux JNI Encoder Test
A simple Spring Webflux application & custom c++ to play with JNI.

## Directories

### jni-test-java-app

The app exposes two endpoints:
- `POST` `/api/v1/encode/native`
  - REQUEST BODY: form-data, looks for a file part with the key `file`
  - WHAT IT DOES: Uses native c++ to BASE64 encode the given file
  - RESPONSE: Json with `request-id` and `encoded-bytes` fields
- `POST` `/api/v1/encode/java`
  - REQUEST BODY: form-data, looks for a file part with the key `file`
  - WHAT IT DOES: Uses java facilities to BASE64 encode the given file
  - RESPONSE: Json with `request-id` and `encoded-bytes` fields

### jni-test-native

The c++ side of things. Builds to a `.dll` / `.so` file which is loaded by the Spring app.

### lib

A pre-built `.dll` / `.so` file of the native code for convenience.
