import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface CustomerModel {
  id: number;
  leadId?: number;
  name: string;
  email: string;
  phone: string;
  source: string;
  lifetimeValue: number;
  createdAt: string;
}

@Injectable({ providedIn: 'root' })
export class CustomerService {
  private url = 'http://localhost:8080/api/contacts';
  constructor(private http: HttpClient) {}
  getAll(): Observable<CustomerModel[]> { return this.http.get<CustomerModel[]>(this.url); }
}