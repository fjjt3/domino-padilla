package com.example.hex.interfaces.websocket;

import com.example.hex.domain.model.DominoGameEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DominoEventHandler {

    private final SimpMessagingTemplate messagingTemplate;

    @EventListener
    public void handleDominoGameEvent(DominoGameEvent event) {
        log.info("Publishing game event: {} for game: {}", event.type(), event.gameId());
        messagingTemplate.convertAndSend("/topic/game/" + event.gameId(), event.payload());
    }
}
