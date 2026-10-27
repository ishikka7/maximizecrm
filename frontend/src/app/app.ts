import { Component, signal } from '@angular/core';
import { LeadList } from './components/lead-list/lead-list';
import { Dashboard } from './components/dashboard/dashboard';
import { Pipeline } from './components/pipeline/pipeline';
import { Campaigns } from './components/campaigns/campaigns';

@Component({
  selector: 'app-root',
  imports: [LeadList, Dashboard, Pipeline, Campaigns],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  tab = signal<'leads' | 'pipeline' | 'campaigns'>('leads');
}