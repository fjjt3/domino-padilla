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
      onConnect: () => {
        console.log('Connected to WebSocket');
      },
      onStompError: (frame) => {
        console.error('STOMP error', frame);
      }
    });

    this.client.activate();
  }

  subscribeToGame(gameId: String): Observable<any> {
    this.client.onConnect = (frame) => {
      this.client.subscribe(`/topic/game/${gameId}`, (message: Message) => {
        this.stateSubject.next(JSON.parse(message.body));
      });
    };
    return this.stateSubject.asObservable();
  }

  startGame(gameId: string, players: string[]): Observable<any> {
    return this.http.post(`${this.apiUrl}/start/${gameId}`, players);
  }

  sendMove(gameId: string, playerId: string, tile: any): void {
    this.client.publish({
      destination: `/app/game/${gameId}/move`,
      body: JSON.stringify({ playerId, tile })
    });
  }
}
