import {Injectable} from '@angular/core';
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {ApprovedIdeasModel} from "../models/approved-ideas.model";
import {PendingIdeasModel} from "../models/pending-ideas.model";

@Injectable({
  providedIn: 'root'
})
export class IdeaService {
  baseUrl: string = "http://localhost:5432/api";

  constructor(private httpClient: HttpClient) {
  }

  fetchApprovedIdeas(): Observable<ApprovedIdeasModel> {
    return this.httpClient.get<ApprovedIdeasModel>(`${this.baseUrl}/idea`)
  }

  fetchPendingIdeas(): Observable<PendingIdeasModel> {
    return this.httpClient.get<PendingIdeasModel>(`${this.baseUrl}/admin`)
  }
}
