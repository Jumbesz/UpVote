import {Injectable} from '@angular/core';
import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {ApprovedIdeasModel} from "../models/approved-ideas.model";
import {PendingIdeasModel} from "../models/pending-ideas.model";
import {IdeaFormDataModel} from "../models/idea-form-data.model";

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


  upVoteIdea(id: number): Observable<string> {
    return this.httpClient.put(`${this.baseUrl}/idea/${id}/upvote`, null, {responseType: 'text'});
  }

  downVoteIdea(id: number): Observable<string> {
    return this.httpClient.put(`${this.baseUrl}/idea/${id}/downvote`, null, {responseType: 'text'});
  }

  approveIdea(id: number): Observable<string> {
    return this.httpClient.put(`${this.baseUrl}/admin/${id}/approve`, null, {responseType: 'text'})
  }

  disapproveIdea(id: number): Observable<string> {
    return this.httpClient.delete(`${this.baseUrl}/admin/${id}/disapprove`, {responseType: 'text'})
  }

  createNewIdea(ideaFormData: IdeaFormDataModel): Observable<any> {
    return this.httpClient.post(`${this.baseUrl}/profile/create-idea`, ideaFormData)
  }
}
