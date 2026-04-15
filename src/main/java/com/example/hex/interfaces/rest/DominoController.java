package com.example.hex.interfaces.rest;

import com.example.hex.domain.model.GameState;
import com.example.hex.domain.service.DominoGameService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/domino")
@CrossOrigin("*")
@RequiredArgsConstructor
public class DominoController {

    private final DominoGameService gameService;

    @PostMapping("/start/{gameId}")
    public GameState startGame(@PathVariable String gameId, @RequestBody List<String> players) {
        return gameService.startGame(gameId, players);
    }

    @GetMapping("/{gameId}")
    public GameState getGameState(@PathVariable String gameId) {
        return gameService.getGameState(gameId);
    }
}
