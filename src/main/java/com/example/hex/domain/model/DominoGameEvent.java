package com.example.hex.domain.model;

public record DominoGameEvent(String gameId, String type, GameState payload) {}
