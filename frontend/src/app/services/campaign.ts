import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface CampaignModel {
  id?: number;
  name: string;
  message: string;
  segment: string;
  status?: string;
  sentCount?: number;
  sentAt?: string;
}

export interface CampaignLogModel {
  id: number;
  campaignId: number;
  leadName: string;
  email: string;
  message: string;
  sentAt: string;
}

@Injectable({ providedIn: 'root' })
export class CampaignService {
  private url = 'http://localhost:8080/api/campaigns';
  constructor(private http: HttpClient) {}

  getAll(): Observable<CampaignModel[]> { return this.http.get<CampaignModel[]>(this.url); }
  create(c: CampaignModel): Observable<CampaignModel> { return this.http.post<CampaignModel>(this.url, c); }
  send(id: number): Observable<CampaignModel> { return this.http.post<CampaignModel>(`${this.url}/${id}/send`, {}); }
  getLogs(id: number): Observable<CampaignLogModel[]> { return this.http.get<CampaignLogModel[]>(`${this.url}/${id}/logs`); }
}