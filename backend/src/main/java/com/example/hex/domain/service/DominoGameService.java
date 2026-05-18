package com.example.hex.domain.service;

import com.example.hex.domain.model.DominoGameEvent;
import com.example.hex.domain.model.DominoTile;
import com.example.hex.domain.model.GameState;
import com.example.hex.domain.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class DominoGameService {

    private final DominoGameEngine engine;
    private final ApplicationEventPublisher eventPublisher;
    private final GameRepository gameRepository;

    public GameState startGame(String gameId, List<String> players) {
        Map<String, List<DominoTile>> hands = engine.deal(players);
        // Force Player1 to start for convenience as requested
        String starter = players.get(0); 
        
        GameState initialState = new GameState(
            gameId,
            new ArrayList<>(),
            hands,
            starter,
            false,
            null,
            new ArrayList<>(players)
        );
        
        gameRepository.save(initialState);
        publishState(gameId, "GAME_STARTED", initialState);
        return initialState;
    }

    public GameState playMove(String gameId, String playerId, DominoTile tile) {
        log.info("Processing move for game {}: player {} tile {}", gameId, playerId, tile);
        GameState currentState = gameRepository.findById(gameId).orElse(null);
        if (currentState == null) {
            log.warn("Game {} not found", gameId);
            return null;
        }
        if (currentState.isGameOver()) {
            log.warn("Game {} is already over", gameId);
            return currentState;
        }
        if (!currentState.currentPlayer().equals(playerId)) {
            log.warn("Not player {}'s turn. Current player: {}", playerId, currentState.currentPlayer());
            return currentState;
        }

        List<DominoTile> playerHand = currentState.playerHands().get(playerId);
        if (playerHand == null || !playerHand.contains(tile)) {
            log.warn("Player {} does not have tile {}", playerId, tile);
            return currentState;
        }

        if (!engine.isValidMove(currentState.board(), tile)) {
            log.warn("Invalid move for tile {} on board {}", tile, currentState.board());
            return currentState;
        }

        log.info("Executing move for tile {}", tile);
        List<DominoTile> newBoard = engine.executeMove(currentState.board(), tile);
        List<DominoTile> newHand = new ArrayList<>(playerHand);
        newHand.remove(tile);

        Map<String, List<DominoTile>> newHands = new HashMap<>(currentState.playerHands());
        newHands.put(playerId, newHand);

        boolean isGameOver = newHand.isEmpty();
        String winner = isGameOver ? playerId : null;
        
        int currentIndex = currentState.playerOrder().indexOf(playerId);
        String nextPlayer = currentState.playerOrder().get((currentIndex + 1) % currentState.playerOrder().size());

        GameState newState = new GameState(
            gameId,
            newBoard,
            newHands,
            nextPlayer,
            isGameOver,
            winner,
            currentState.playerOrder()
        );

        gameRepository.save(newState);
        publishState(gameId, "MOVE_MADE", newState);

        // If next player is 'Machine', trigger AI move
        if (!isGameOver && nextPlayer.equals("Machine")) {
            triggerAiMove(gameId);
        }

        return newState;
    }

    private void triggerAiMove(String gameId) {
        // Simple async simulation of Machine's turn
        new Thread(() -> {
            try {
                Thread.sleep(1500); // 1.5s delay
                makeAiMove(gameId);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }

    private void makeAiMove(String gameId) {
        GameState state = gameRepository.findById(gameId).orElse(null);
        if (state == null || state.isGameOver()) return;

        List<DominoTile> machineHand = state.playerHands().get("Machine");
        Optional<DominoTile> validTile = machineHand.stream()
                .filter(t -> engine.isValidMove(state.board(), t))
                .findFirst();

        if (validTile.isPresent()) {
            playMove(gameId, "Machine", validTile.get());
        } else {
            // Machine has to pass, skip to next player (Human)
            GameState passedState = new GameState(
                state.gameId(),
                state.board(),
                state.playerHands(),
                state.playerOrder().get(0), // Back to Human
                false,
                null,
                state.playerOrder()
            );
            gameRepository.save(passedState);
            publishState(gameId, "MACHINE_PASSED", passedState);
        }
    }

    private void publishState(String gameId, String type, GameState state) {
        eventPublisher.publishEvent(new DominoGameEvent(gameId, type, state));
    }
    
    public GameState getGameState(String gameId) {
        return gameRepository.findById(gameId).orElse(null);
    }
}
