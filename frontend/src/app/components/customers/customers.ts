import { Component, OnInit, computed, signal } from '@angular/core';
import { CurrencyPipe, DatePipe } from '@angular/common';
import { CustomerModel, CustomerService } from '../../services/customer';

@Component({
  selector: 'app-customers',
  imports: [CurrencyPipe, DatePipe],
  templateUrl: './customers.html',
})
export class Customers implements OnInit {
  customers = signal<CustomerModel[]>([]);
  totalValue = computed(() => this.customers().reduce((s, c) => s + (c.lifetimeValue ?? 0), 0));

  constructor(private svc: CustomerService) {}
  ngOnInit() { this.svc.getAll().subscribe(c => this.customers.set(c)); }

  initials(name: string): string {
    return (name || '?').split(' ').map(p => p[0]).slice(0, 2).join('').toUpperCase();
  }
}