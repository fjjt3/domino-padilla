package com.example.hex.domain.repository;

import com.example.hex.domain.model.GameState;
import java.util.Optional;

public interface GameRepository {
    void save(GameState state);
    Optional<GameState> findById(String gameId);
}
