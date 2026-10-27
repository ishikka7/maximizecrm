import { Component, OnInit, computed, signal } from '@angular/core';
import { CurrencyPipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { OpportunityModel, OpportunityService } from '../../services/opportunity';

@Component({
  selector: 'app-pipeline',
  imports: [FormsModule, CurrencyPipe],
  templateUrl: './pipeline.html',
})
export class Pipeline implements OnInit {
  stages = ['Prospecting', 'Proposal', 'Negotiation', 'Won', 'Lost'];
  flow = ['Prospecting', 'Proposal', 'Negotiation', 'Won'];
  deals = signal<OpportunityModel[]>([]);
  form: OpportunityModel = { name: '', amount: 10000, stage: 'Prospecting' };

  openValue = computed(() =>
    this.deals().filter(d => d.stage !== 'Won' && d.stage !== 'Lost').reduce((s, d) => s + d.amount, 0));
  wonValue = computed(() =>
    this.deals().filter(d => d.stage === 'Won').reduce((s, d) => s + d.amount, 0));
  winRate = computed(() => {
    const won = this.deals().filter(d => d.stage === 'Won').length;
    const lost = this.deals().filter(d => d.stage === 'Lost').length;
    return won + lost === 0 ? 0 : Math.round((won * 100) / (won + lost));
  });

  constructor(private svc: OpportunityService) {}
  ngOnInit() { this.load(); }

  load() { this.svc.getAll().subscribe(d => this.deals.set(d)); }
  byStage(st: string) { return this.deals().filter(d => d.stage === st); }
  stageTotal(st: string) { return this.byStage(st).reduce((s, d) => s + d.amount, 0); }

  add() {
    if (!this.form.name.trim()) return;
    this.svc.create(this.form).subscribe(() => {
      this.form = { name: '', amount: 10000, stage: 'Prospecting' };
      this.load();
    });
  }

  move(d: OpportunityModel, stage: string) {
    this.svc.moveStage(d.id!, stage).subscribe(() => this.load());
  }
  next(d: OpportunityModel) {
    const i = this.flow.indexOf(d.stage);
    if (i >= 0 && i < this.flow.length - 1) this.move(d, this.flow[i + 1]);
  }
  back(d: OpportunityModel) {
    const i = this.flow.indexOf(d.stage);
    if (i > 0) this.move(d, this.flow[i - 1]);
  }
  canBack(d: OpportunityModel) { return d.stage === 'Proposal' || d.stage === 'Negotiation'; }
  isOpen(d: OpportunityModel) { return d.stage !== 'Won' && d.stage !== 'Lost'; }

  remove(d: OpportunityModel) {
    if (confirm('Delete this deal?')) this.svc.delete(d.id!).subscribe(() => this.load());
  }

  stageClass(st: string): string {
    switch (st) {
      case 'Prospecting': return 'bg-secondary';
      case 'Proposal': return 'bg-info text-dark';
      case 'Negotiation': return 'bg-warning text-dark';
      case 'Won': return 'bg-success';
      default: return 'bg-danger';
    }
  }
}