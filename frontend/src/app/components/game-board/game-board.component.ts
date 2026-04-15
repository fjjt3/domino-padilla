import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DominoService } from '../../services/domino.service';
import { TileComponent } from '../tile/tile.component';

@Component({
  selector: 'app-game-board',
  standalone: true,
  imports: [CommonModule, TileComponent],
  template: `
    <div class="game-container">
      <div class="header" [class.glass-panel]="gameState">
        <h1>Bar Padilla - Tatooine Cantina</h1>
        <div class="game-info" *ngIf="gameState">
          <span>Game ID: {{ gameState.gameId }}</span>
          <span>Next Turn: <b [style.color]="'#02eaff'">{{ gameState.currentPlayer }}</b></span>
        </div>
      </div>

      <div class="board-area" [class.glass-panel]="gameState">
        <div *ngIf="!gameState" class="start-game-overlay">
          <h1 class="welcome-title">Welcome to Bar Padilla</h1>
          <button class="start-btn" (click)="initGame()">Start New Game</button>
        </div>

        <div class="active-ends" *ngIf="gameState && gameState.board.length > 0">
          <span class="end-marker">Left end: {{ gameState.board[0].left }}</span>
          <div class="tiles-row">
            <app-tile *ngFor="let tile of gameState.board" 
                      [left]="tile.left" 
                      [right]="tile.right" 
                      [horizontal]="true">
            </app-tile>
          </div>
          <span class="end-marker">Right end: {{ gameState.board[gameState.board.length - 1].right }}</span>
        </div>
      </div>

      <div class="player-area glass-panel">
        <h3>Your Hand ({{ playerId }})</h3>
        <div class="hand">
          <app-tile *ngFor="let tile of myHand" 
                    [left]="tile.left" 
                    [right]="tile.right"
                    (click)="playMove(tile)">
          </app-tile>
        </div>
      </div>

      <div class="winner-overlay" *ngIf="gameState?.isGameOver">
        <div class="winner-content glass-panel">
          <h2>VICTORY FOR {{ gameState.winner }}!</h2>
          <button (click)="resetGame()">Return to Cantina</button>
        </div>
      </div>
    </div>
  `,
  styles: [`
    .game-container { padding: 20px; display: flex; flex-direction: column; gap: 20px; height: 90vh; }
    .header { padding: 10px 20px; display: flex; justify-content: space-between; align-items: center; }
    .board-area { flex: 1; display: flex; align-items: center; justify-content: center; overflow-x: auto; padding: 40px; }
    .active-ends { display: flex; align-items: center; gap: 20px; }
    .end-marker { 
      background: var(--neon-blue); 
      color: #000; 
      padding: 5px 10px; 
      border-radius: 4px; 
      font-weight: bold; 
      font-size: 0.8rem;
      text-transform: uppercase;
      box-shadow: 0 0 10px var(--neon-blue);
    }
    .tiles-row { display: flex; align-items: center; gap: 5px; }
    .player-area { padding: 20px; }
    .hand { display: flex; gap: 15px; justify-content: center; }
    .winner-overlay { position: fixed; top: 0; left: 0; width: 100%; height: 100%; background: rgba(0,0,0,0.8); display: flex; align-items: center; justify-content: center; z-index: 100; }
    .winner-content { padding: 40px; text-align: center; }
    .start-game-overlay { text-align: center; }
    .welcome-title { 
      font-size: 4rem; 
      color: #fff; 
      text-transform: uppercase; 
      margin-bottom: 30px;
      text-shadow: 0 0 10px #ff00de, 0 0 20px #ff00de, 0 0 30px #ff00de;
      font-family: 'Space Grotesk', sans-serif;
    }
    .start-btn { 
      padding: 15px 30px; 
      font-size: 1.2rem; 
      background: var(--neon-blue); 
      color: #000; 
      border: none; 
      border-radius: 8px; 
      cursor: pointer; 
      font-family: 'Outfit', sans-serif;
      text-transform: uppercase;
      font-weight: bold;
      box-shadow: 0 0 20px var(--neon-blue);
      transition: transform 0.2s;
    }
    .start-btn:hover { transform: scale(1.1); box-shadow: 0 0 30px var(--neon-blue); }
  `]
})
export class GameBoardComponent implements OnInit {
  gameState: any;
  gameId: string = 'tatooine-1';
  playerId: string = 'Player1'; // Simplified for demo
  myHand: any[] = [];

  constructor(private dominoService: DominoService) {}

  ngOnInit() {
    this.dominoService.subscribeToGame(this.gameId).subscribe(state => {
      this.gameState = state;
      if (state) {
        this.myHand = state.playerHands[this.playerId] || [];
      }
    });
  }

  initGame() {
    const players = ['Player1', 'Machine'];
    this.dominoService.startGame(this.gameId, players).subscribe();
  }

  playMove(tile: any) {
    if (this.gameState.currentPlayer !== this.playerId) {
      alert(`It is ${this.gameState.currentPlayer}'s turn! You must wait.`);
      return;
    }
    
    console.log('Attempting move:', tile);
    console.log('Board ends:', 
      this.gameState.board[0].left, 
      this.gameState.board[this.gameState.board.length - 1].right
    );

    this.dominoService.sendMove(this.gameId, this.playerId, tile);
  }

  resetGame() {
    window.location.reload();
  }
}
