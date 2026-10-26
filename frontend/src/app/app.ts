import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { LeadList } from './components/lead-list/lead-list';
import { Dashboard } from './components/dashboard/dashboard';
@Component({
  selector: 'app-root',
  imports: [RouterOutlet, LeadList, Dashboard],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('frontend');
}
