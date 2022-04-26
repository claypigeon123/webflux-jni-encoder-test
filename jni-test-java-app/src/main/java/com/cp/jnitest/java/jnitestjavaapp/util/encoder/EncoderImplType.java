package com.cp.jnitest.java.jnitestjavaapp.util.encoder;

public enum EncoderImplType {
    NATIVE("native"),
    JAVA("java");

    private final String displayName;

    EncoderImplType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static EncoderImplType fromDisplayName(String displayName) throws IllegalArgumentException {
        for (EncoderImplType type : EncoderImplType.values()) {
            if (type.getDisplayName().equals(displayName)) {
                return type;
            }
        }

        throw new IllegalArgumentException("EncoderImplType has no constant with the specified display name");
    }


}
