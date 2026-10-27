import { Component, signal } from '@angular/core';
import { LeadList } from './components/lead-list/lead-list';
import { Dashboard } from './components/dashboard/dashboard';
import { Pipeline } from './components/pipeline/pipeline';
import { Campaigns } from './components/campaigns/campaigns';
import { Customers } from './components/customers/customers';

@Component({
  selector: 'app-root',
  imports: [LeadList, Dashboard, Pipeline, Campaigns, Customers],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  tab = signal<'leads' | 'pipeline' | 'customers' | 'campaigns'>('leads');
}