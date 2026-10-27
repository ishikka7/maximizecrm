import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface OpportunityModel {
  id?: number;
  name: string;
  leadId?: number;
  leadName?: string;
  amount: number;
  stage: string;
}

@Injectable({ providedIn: 'root' })
export class OpportunityService {
  private url = 'http://localhost:8080/api/opportunities';
  constructor(private http: HttpClient) {}

  getAll(): Observable<OpportunityModel[]> { return this.http.get<OpportunityModel[]>(this.url); }
  create(o: OpportunityModel): Observable<OpportunityModel> { return this.http.post<OpportunityModel>(this.url, o); }
  moveStage(id: number, stage: string): Observable<OpportunityModel> {
    return this.http.put<OpportunityModel>(`${this.url}/${id}/stage`, null, { params: { stage } });
  }
  delete(id: number): Observable<void> { return this.http.delete<void>(`${this.url}/${id}`); }
}