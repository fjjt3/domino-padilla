package com.example.hex.domain.service;

import com.example.hex.domain.model.DominoTile;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DominoGameEngine {

    public List<DominoTile> createFullSet() {
        List<DominoTile> tiles = new ArrayList<>();
        for (int i = 0; i <= 6; i++) {
            for (int j = i; j <= 6; j++) {
                tiles.add(new DominoTile(i, j));
            }
        }
        return tiles;
    }

    public Map<String, List<DominoTile>> deal(List<String> players) {
        List<DominoTile> deck = createFullSet();
        Collections.shuffle(deck);
        
        Map<String, List<DominoTile>> hands = new HashMap<>();
        int tilesPerPlayer = 7; // standard rules: always 7 per player 
        
        for (int i = 0; i < players.size(); i++) {
            int start = i * tilesPerPlayer;
            int end = start + tilesPerPlayer;
            hands.put(players.get(i), new ArrayList<>(deck.subList(start, end)));
        }
        
        return hands;
    }

    public String findStarter(Map<String, List<DominoTile>> hands) {
        // Find highest double, starting from 6-6
        for (int i = 6; i >= 0; i--) {
            DominoTile target = new DominoTile(i, i);
            for (Map.Entry<String, List<DominoTile>> entry : hands.entrySet()) {
                if (entry.getValue().contains(target)) {
                    return entry.getKey();
                }
            }
        }
        // If no doubles (unlikely in full deal), first player starts (simplified for now)
        return hands.keySet().iterator().next();
    }
    public boolean isValidMove(List<DominoTile> board, DominoTile tile) {
        if (board.isEmpty()) return true;
        DominoTile first = board.get(0);
        DominoTile last = board.get(board.size() - 1);
        return tile.left() == first.left() || tile.right() == first.left() ||
               tile.left() == last.right() || tile.right() == last.right();
    }

    public List<DominoTile> executeMove(List<DominoTile> board, DominoTile tile) {
        List<DominoTile> newBoard = new ArrayList<>(board);
        if (newBoard.isEmpty()) {
            newBoard.add(tile);
        } else {
            DominoTile first = newBoard.get(0);
            DominoTile last = newBoard.get(newBoard.size() - 1);
            
            if (tile.right() == first.left()) {
                newBoard.add(0, tile);
            } else if (tile.left() == first.left()) {
                newBoard.add(0, new DominoTile(tile.right(), tile.left()));
            } else if (tile.left() == last.right()) {
                newBoard.add(tile);
            } else if (tile.right() == last.right()) {
                newBoard.add(new DominoTile(tile.right(), tile.left()));
            }
        }
        return newBoard;
    }
}
