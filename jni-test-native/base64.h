#pragma once

#include <string>

namespace cp {
    typedef unsigned char uchar;

    struct encode_holder {
        char* encoded_bytes;
        size_t length;
    };

    static const std::string b = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";

    // ---

    static cp::encode_holder* base64_encode(char* plain_bytes, size_t plain_length) {
        std::string plain_str(plain_bytes, plain_length);
        std::string encoded_str;

        int val = 0, valb = -6;
        for (uchar c : plain_str) {
            val = (val << 8) + c;
            valb += 8;
            while (valb >= 0) {
                encoded_str.push_back(b[(val >> valb) & 0x3F]);
                valb -= 6;
            }
        }
        if (valb > -6) encoded_str.push_back(b[((val << 8) >> (valb + 8)) & 0x3F]);
        while (encoded_str.size() % 4) encoded_str.push_back('=');

        cp::encode_holder* encode_holder = new cp::encode_holder();
        encode_holder->length = encoded_str.size();
        encode_holder->encoded_bytes = new char[encode_holder->length];

        for (int i = 0; i < encode_holder->length; i++) {
            encode_holder->encoded_bytes[i] = encoded_str.at(i);
        }

        return encode_holder;
    }

    static void free_encode_holder(cp::encode_holder* encode_holder) {
        delete[] encode_holder->encoded_bytes;
        delete encode_holder;
    }
}