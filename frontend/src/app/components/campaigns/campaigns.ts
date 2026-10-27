import { Component, OnInit, signal } from '@angular/core';
import { DatePipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CampaignLogModel, CampaignModel, CampaignService } from '../../services/campaign';

@Component({
  selector: 'app-campaigns',
  imports: [FormsModule, DatePipe],
  templateUrl: './campaigns.html',
})
export class Campaigns implements OnInit {
  segments = [
    { code: 'ALL', label: 'All leads' },
    { code: 'HOT', label: 'Hot leads (score 60+)' },
    { code: 'QUALIFIED', label: 'Qualified leads' },
    { code: 'REFERRAL', label: 'Referral leads' },
    { code: 'WEBSITE', label: 'Website leads' },
    { code: 'STORE_VISIT', label: 'Store Visit leads' },
  ];
  hint = 'Tip: write {name} in the message to personalise it for each lead.';

  campaigns = signal<CampaignModel[]>([]);
  logs = signal<CampaignLogModel[]>([]);
  openCampaign = signal<CampaignModel | null>(null);

  form: CampaignModel = this.blank();

  constructor(private svc: CampaignService) {}
  ngOnInit() { this.load(); }

  blank(): CampaignModel {
    return { name: '', message: 'Hi {name}, enjoy 20% off at RetailMax this week!', segment: 'HOT' };
  }

  load() { this.svc.getAll().subscribe(c => this.campaigns.set(c)); }

  create() {
    if (!this.form.name.trim()) return;
    this.svc.create(this.form).subscribe(() => {
      this.form = this.blank();
      this.load();
    });
  }

  send(c: CampaignModel) {
    this.svc.send(c.id!).subscribe(updated => {
      this.load();
      this.showLogs(updated);
    });
  }

  showLogs(c: CampaignModel) {
    this.openCampaign.set(c);
    this.svc.getLogs(c.id!).subscribe(l => this.logs.set(l));
  }
  closeLogs() { this.openCampaign.set(null); this.logs.set([]); }

  segmentLabel(code: string): string {
    return this.segments.find(s => s.code === code)?.label ?? code;
  }
}