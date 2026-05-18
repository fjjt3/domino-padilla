package com.example.hex.domain.service;

import com.example.hex.domain.model.DominoTile;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

public class DominoGameEngineTest {

    private final DominoGameEngine engine = new DominoGameEngine();

    @Test
    void shouldInitializeFullSetOfTiles() {
        List<DominoTile> tiles = engine.createFullSet();
        assertThat(tiles).hasSize(28); // 0-0 to 6-6
    }

    @Test
    void shouldDistributeTilesToPlayers() {
        List<String> players = List.of("Player1", "Player2", "Player3", "Player4");
        Map<String, List<DominoTile>> hands = engine.deal(players);
        
        assertThat(hands).hasSize(4);
        hands.values().forEach(hand -> assertThat(hand).hasSize(7));
    }

    @Test
    void shouldIdentifyStartingPlayer() {
        // Player with 6-6 starts
        DominoTile doubleSix = new DominoTile(6, 6);
        Map<String, List<DominoTile>> hands = Map.of(
            "P1", List.of(doubleSix, new DominoTile(0, 0)),
            "P2", List.of(new DominoTile(1, 1), new DominoTile(2, 2))
        );
        
        String starter = engine.findStarter(hands);
        assertThat(starter).isEqualTo("P1");
    }
}
