package com.example.hex.domain.model;

public record DominoTile(int left, int right) {
    public boolean isDouble() {
        return left == right;
    }
}
