package com.securefromscratch.busybee.safety;

public class ImageName {

    private final String name;

    public ImageName(String name) {
        if (name != null && name.contains("..")) {
            throw new IllegalArgumentException("Image name must not contain path traversal characters.");
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}