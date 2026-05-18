package com.example.hex.infrastructure.repository;

import com.example.hex.domain.model.GameState;
import com.example.hex.domain.repository.GameRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryGameRepository implements GameRepository {
    private final Map<String, GameState> games = new ConcurrentHashMap<>();

    @Override
    public void save(GameState state) {
        games.put(state.gameId(), state);
    }

    @Override
    public Optional<GameState> findById(String gameId) {
        return Optional.ofNullable(games.get(gameId));
    }
}
