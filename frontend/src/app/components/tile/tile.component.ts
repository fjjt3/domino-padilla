import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';

// Standard domino pip positions (% of half-tile)
// Each entry: [top%, left%]
const PIP_POSITIONS: Record<number, [number, number][]> = {
  0: [],
  1: [[50, 50]],
  2: [[25, 75], [75, 25]],
  3: [[20, 80], [50, 50], [80, 20]],
  4: [[25, 25], [25, 75], [75, 25], [75, 75]],
  5: [[25, 25], [25, 75], [50, 50], [75, 25], [75, 75]],
  6: [[25, 25], [25, 75], [50, 25], [50, 75], [75, 25], [75, 75]],
};

@Component({
  selector: 'app-tile',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="domino-tile" [class.horizontal]="horizontal">
      <div class="half">
        <div *ngFor="let pos of getPips(left)"
             class="pip"
             [style.top.%]="pos[0]"
             [style.left.%]="pos[1]">
        </div>
      </div>
      <div class="divider"></div>
      <div class="half">
        <div *ngFor="let pos of getPips(right)"
             class="pip"
             [style.top.%]="pos[0]"
             [style.left.%]="pos[1]">
        </div>
      </div>
    </div>
  `,
  styles: [`
    .domino-tile {
      width: 50px;
      height: 100px;
      background: #fdfdfd;
      border-radius: 6px;
      display: flex;
      flex-direction: column;
      cursor: pointer;
      box-sizing: border-box;
      box-shadow: 2px 2px 4px rgba(0,0,0,0.5);
      border: 1px solid #ccc;
      transition: transform 0.15s, box-shadow 0.15s;
    }
    .domino-tile:hover {
      transform: translateY(-5px);
      box-shadow: 0 0 15px var(--neon-blue, #00d4ff);
    }
    .domino-tile.horizontal {
      transform: rotate(90deg);
      margin: 0 25px;
    }
    .domino-tile.horizontal:hover {
      transform: translateY(-5px) rotate(90deg);
    }
    .divider {
      height: 2px;
      background: #999;
      flex-shrink: 0;
    }
    .half {
      flex: 1;
      position: relative;
    }
    .pip {
      width: 9px;
      height: 9px;
      background: #222;
      border-radius: 50%;
      position: absolute;
      transform: translate(-50%, -50%);
    }
  `]
})
export class TileComponent {
  @Input() left: number = 0;
  @Input() right: number = 0;
  @Input() horizontal: boolean = false;

  getPips(num: number): [number, number][] {
    return PIP_POSITIONS[num] ?? [];
  }

  getDots(num: number): any[] {
    return Array(num).fill(0);
  }
}

