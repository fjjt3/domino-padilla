import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-tile',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="domino-tile" [class.horizontal]="horizontal">
      <div class="dot-section dots-{{left}}">
        <div *ngFor="let dot of getDots(left)" class="dot"></div>
      </div>
      <div class="divider"></div>
      <div class="dot-section dots-{{right}}">
        <div *ngFor="let dot of getDots(right)" class="dot"></div>
      </div>
    </div>
  `,
  styles: [`
    .domino-tile {
      width: 50px; height: 100px; background: #fff; border-radius: 6px;
      display: flex; flex-direction: column; cursor: pointer;
      position: relative; box-sizing: border-box; box-shadow: 2px 2px 4px rgba(0,0,0,0.5);
      border: 1px solid #ccc;
    }
    .domino-tile.horizontal { transform: rotate(90deg); margin: 0 25px; }
    .domino-tile:hover { transform: translateY(-5px); box-shadow: 0 0 15px var(--neon-blue); z-index: 10; }
    .domino-tile.horizontal:hover { transform: translateY(-5px) rotate(90deg); }
    .divider { height: 2px; background: #888; width: 100%; box-sizing: border-box; }
    .dot-section { flex: 1; position: relative; width: 100%; }
    .dot { width: 8px; height: 8px; background: #222; border-radius: 50%; position: absolute; }
    
    /* Center (1, 3, 5) */
    .dots-1 .dot:nth-child(1), .dots-3 .dot:nth-child(2), .dots-5 .dot:nth-child(5) { top: 50%; left: 50%; transform: translate(-50%, -50%); }
    
    /* Top Left & Bottom Right (2, 3, 4, 5, 6) */
    .dots-2 .dot:nth-child(1), .dots-3 .dot:nth-child(1), .dots-4 .dot:nth-child(1), .dots-5 .dot:nth-child(1), .dots-6 .dot:nth-child(1) { top: 15%; left: 15%; }
    .dots-2 .dot:nth-child(2), .dots-3 .dot:nth-child(3), .dots-4 .dot:nth-child(4), .dots-5 .dot:nth-child(4), .dots-6 .dot:nth-child(6) { bottom: 15%; right: 15%; }
    
    /* Top Right & Bottom Left (4, 5, 6) */
    .dots-4 .dot:nth-child(2), .dots-5 .dot:nth-child(2), .dots-6 .dot:nth-child(2) { top: 15%; right: 15%; }
    .dots-4 .dot:nth-child(3), .dots-5 .dot:nth-child(3), .dots-6 .dot:nth-child(5) { bottom: 15%; left: 15%; }

    /* Middle Left & Middle Right (6) */
    .dots-6 .dot:nth-child(3) { top: 50%; left: 15%; transform: translateY(-50%); }
    .dots-6 .dot:nth-child(4) { top: 50%; right: 15%; transform: translateY(-50%); }
  `]
})
export class TileComponent {
  @Input() left: number = 0;
  @Input() right: number = 0;
  @Input() horizontal: boolean = false;

  getDots(num: number): any[] {
    return Array(num).fill(0);
  }
}
