package com.example.hex.domain.model;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class DominoTileTest {

    @Test
    void shouldCreateTileWithValues() {
        DominoTile tile = new DominoTile(6, 6);
        assertThat(tile.left()).isEqualTo(6);
        assertThat(tile.right()).isEqualTo(6);
    }

    @Test
    void shouldBeDoubleIfValuesAreEqual() {
        DominoTile tile = new DominoTile(5, 5);
        assertThat(tile.isDouble()).isTrue();
    }

    @Test
    void shouldNotBeDoubleIfValuesAreDifferent() {
        DominoTile tile = new DominoTile(1, 2);
        assertThat(tile.isDouble()).isFalse();
    }
}
