package com.example.hex.interfaces.websocket;

import com.example.hex.domain.model.DominoTile;
import com.example.hex.domain.service.DominoGameService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@Slf4j
public class DominoWebSocketController {

    private final DominoGameService gameService;

    @MessageMapping("/game/{gameId}/move")
    public void handleMove(@DestinationVariable String gameId, MoveRequest request) {
        log.info("Received move request for game {}: {} plays {} on side {}", gameId, request.getPlayerId(), request.getTile(), request.getSide());
        gameService.playMove(gameId, request.getPlayerId(), request.getTile(), request.getSide());
    }

    @MessageMapping("/game/{gameId}/pass")
    public void handlePass(@DestinationVariable String gameId, PassRequest request) {
        log.info("Received pass request for game {}: {}", gameId, request.getPlayerId());
        gameService.passTurn(gameId, request.getPlayerId());
    }

    @Data
    public static class MoveRequest {
        private String playerId;
        private DominoTile tile;
        private String side;
    }

    @Data
    public static class PassRequest {
        private String playerId;
    }
}
