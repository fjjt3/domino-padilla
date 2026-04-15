import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-tile',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="domino-tile" [class.horizontal]="horizontal">
      <div class="dot-section">
        <div *ngFor="let dot of getDots(left)" class="dot"></div>
      </div>
      <div class="dot-section">
        <div *ngFor="let dot of getDots(right)" class="dot"></div>
      </div>
    </div>
  `,
  styles: [`
    .horizontal { transform: rotate(90deg); margin: 0 25px; }
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
