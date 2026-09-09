import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Client, Message } from '@stomp/stompjs';
import SockJS from 'sockjs-client';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DominoService {
  private client: Client;
  private stateSubject = new BehaviorSubject<any>(null);
  private apiUrl = 'http://localhost:8080/api/domino';

  constructor(private http: HttpClient) {
    this.client = new Client({
      webSocketFactory: () => new SockJS('http://localhost:8080/domino-websocket'),
      reconnectDelay: 5000,
      onStompError: (frame) => {
        console.error('STOMP error', frame);
      }
    });

    this.client.activate();
  }

  subscribeToGame(gameId: string): Observable<any> {
    const doSubscribe = () => {
      console.log('Subscribing to game topic:', gameId);
      this.client.subscribe(`/topic/game/${gameId}`, (message: Message) => {
        const state = JSON.parse(message.body);
        console.log('Received game state update, currentPlayer:', state.currentPlayer);
        this.stateSubject.next(state);
      });
    };

    if (this.client.connected) {
      // Already connected, subscribe immediately
      doSubscribe();
    } else {
      // Wait for connection
      this.client.onConnect = () => {
        console.log('Connected to WebSocket');
        doSubscribe();
      };
    }

    return this.stateSubject.asObservable();
  }

  startGame(gameId: string, players: string[]): Observable<any> {
    return this.http.post(`${this.apiUrl}/start/${gameId}`, players);
  }

  sendMove(gameId: string, playerId: string, tile: any, side?: string): void {
    console.log('Sending move:', { playerId, tile, side });
    this.client.publish({
      destination: `/app/game/${gameId}/move`,
      body: JSON.stringify({ playerId, tile, side })
    });
  }

  sendPass(gameId: string, playerId: string): void {
    this.client.publish({
      destination: `/app/game/${gameId}/pass`,
      body: JSON.stringify({ playerId })
    });
  }
}

