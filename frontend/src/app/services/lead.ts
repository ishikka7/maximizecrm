import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface LeadModel {
  id?: number;
  name: string;
  email: string;
  phone: string;
  source: string;
  status: string;
  score?: number;
  createdAt?: string;
}

export interface HistoryModel {
  id: number;
  leadId: number;
  oldStatus: string | null;
  newStatus: string;
  changedAt: string;
}

export interface LeadStats {
  total: number;
  avgScore: number;
  conversionRate: number;
  byStatus: Record<string, number>;
  bySource: Record<string, number>;
}

@Injectable({ providedIn: 'root' })
export class LeadService {
  private url = 'http://localhost:8080/api/leads';
  constructor(private http: HttpClient) {}

  getAll(): Observable<LeadModel[]> { return this.http.get<LeadModel[]>(this.url); }
  create(l: LeadModel): Observable<LeadModel> { return this.http.post<LeadModel>(this.url, l); }
  update(id: number, l: LeadModel): Observable<LeadModel> { return this.http.put<LeadModel>(`${this.url}/${id}`, l); }
  delete(id: number): Observable<void> { return this.http.delete<void>(`${this.url}/${id}`); }
  getHistory(id: number): Observable<HistoryModel[]> { return this.http.get<HistoryModel[]>(`${this.url}/${id}/history`); }
  getStats(): Observable<LeadStats> { return this.http.get<LeadStats>(`${this.url}/stats`); }
}