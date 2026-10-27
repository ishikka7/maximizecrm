import { Component, OnInit, computed, output, signal } from '@angular/core';
import { DatePipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HistoryModel, LeadModel, LeadService } from '../../services/lead';

@Component({
  selector: 'app-lead-list',
  imports: [FormsModule, DatePipe],
  templateUrl: './lead-list.html',
})
export class LeadList implements OnInit {
  leads = signal<LeadModel[]>([]);
  search = signal('');
  statusFilter = signal('');
  statuses = ['New', 'Contacted', 'Qualified', 'Lost'];
  sources = ['Website', 'Referral', 'Store Visit'];

  history = signal<HistoryModel[]>([]);
  historyLead = signal<LeadModel | null>(null);

  filtered = computed(() =>
    this.leads().filter(l =>
      l.name.toLowerCase().includes(this.search().toLowerCase()) &&
      (!this.statusFilter() || l.status === this.statusFilter())
    )
  );
  changed = output<void>();
  form: LeadModel = this.blank();

  constructor(private svc: LeadService) {}
  ngOnInit() { this.load(); }

  blank(): LeadModel { return { name: '', email: '', phone: '', source: 'Website', status: 'New' }; }
  load() { this.svc.getAll().subscribe(d => this.leads.set(d)); }

  save() {
    const req = this.form.id ? this.svc.update(this.form.id, this.form) : this.svc.create(this.form);
    req.subscribe(() => {
      this.form = this.blank();
      this.load();
      this.changed.emit();
      const open = this.historyLead();
      if (open) this.showHistory(open);
    });
  }

  edit(l: LeadModel) { this.form = { ...l }; }

  remove(id: number) {
    if (confirm('Delete this lead?')) {
      this.svc.delete(id).subscribe(() => {
        if (this.historyLead()?.id === id) this.closeHistory();
        this.load();
        this.changed.emit();
      });
    }
  }

  showHistory(l: LeadModel) {
    this.historyLead.set(l);
    this.svc.getHistory(l.id!).subscribe(h => this.history.set(h));
  }
  closeHistory() { this.historyLead.set(null); this.history.set([]); }

  scoreLabel(score?: number): string {
    const s = score ?? 0;
    return s >= 60 ? 'Hot' : s >= 40 ? 'Warm' : 'Cold';
  }
  scoreClass(score?: number): string {
    const s = score ?? 0;
    return s >= 60 ? 'bg-danger' : s >= 40 ? 'bg-warning text-dark' : 'bg-secondary';
  }
    statusClass(s: string): string {
    switch (s) {
      case 'New': return 'pill-new';
      case 'Contacted': return 'pill-contacted';
      case 'Qualified': return 'pill-qualified';
      default: return 'pill-lost';
    }
  }

  initials(name: string): string {
    return (name || '?').split(' ').map(p => p[0]).slice(0, 2).join('').toUpperCase();
  }

  cancelEdit() { this.form = this.blank(); }
}