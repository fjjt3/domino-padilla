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
        log.info("Received move request for game {}: {} plays {}", gameId, request.getPlayerId(), request.getTile());
        gameService.playMove(gameId, request.getPlayerId(), request.getTile());
    }

    @Data
    public static class MoveRequest {
        private String playerId;
        private DominoTile tile;
    }
}
