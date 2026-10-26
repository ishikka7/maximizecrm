import { AfterViewInit, Component, ElementRef, ViewChild, signal } from '@angular/core';
import { Chart, registerables } from 'chart.js';
import { LeadService, LeadStats } from '../../services/lead';

Chart.register(...registerables);

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.html',
})
export class Dashboard implements AfterViewInit {
  @ViewChild('statusChart') statusCanvas!: ElementRef<HTMLCanvasElement>;
  @ViewChild('sourceChart') sourceCanvas!: ElementRef<HTMLCanvasElement>;

  stats = signal<LeadStats | null>(null);
  private statusChart?: Chart;
  private sourceChart?: Chart;

  constructor(private svc: LeadService) {}

  ngAfterViewInit() { this.refresh(); }

  refresh() {
    this.svc.getStats().subscribe(s => {
      this.stats.set(s);
      this.draw(s);
    });
  }

  private draw(s: LeadStats) {
    this.statusChart?.destroy();
    this.sourceChart?.destroy();

    this.statusChart = new Chart(this.statusCanvas.nativeElement, {
      type: 'doughnut',
      data: {
        labels: Object.keys(s.byStatus),
        datasets: [{
          data: Object.values(s.byStatus),
          backgroundColor: ['#0d6efd', '#ffc107', '#198754', '#dc3545'],
        }],
      },
      options: { responsive: true, maintainAspectRatio: false },
    });

    this.sourceChart = new Chart(this.sourceCanvas.nativeElement, {
      type: 'bar',
      data: {
        labels: Object.keys(s.bySource),
        datasets: [{
          label: 'Leads',
          data: Object.values(s.bySource),
          backgroundColor: '#6f42c1',
        }],
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        plugins: { legend: { display: false } },
        scales: { y: { beginAtZero: true, ticks: { precision: 0 } } },
      },
    });
  }
}