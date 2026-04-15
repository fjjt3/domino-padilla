package com.example.hex.domain.model;

import java.util.List;
import java.util.Map;

public record GameState(
    String gameId,
    List<DominoTile> board,
    Map<String, List<DominoTile>> playerHands,
    String currentPlayer,
    boolean isGameOver,
    String winner,
    List<String> playerOrder
) {}
