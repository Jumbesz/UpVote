import {Component, OnInit} from '@angular/core';
import {IdeaService} from "../../services/idea.service";
import {AuthenticationService} from "../../services/authentication.service";
import {Router} from "@angular/router";
import {IdeaModel} from "../../models/idea.model";

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {
  pendingIdeas: IdeaModel[] = [];
  approvedIdeas: IdeaModel[] = [];


  constructor(private ideaService: IdeaService,
              private authService: AuthenticationService,
              private router: Router) {
  }

  ngOnInit(): void {
    this.fetchApprovedIdeas();
    this.fetchPendingIdeas();
  }


  logout() {
    this.authService.logout();
    this.router.navigate(['home'])
  }

  approveIdea(id: number) {
    this.ideaService.approveIdea(id).subscribe(() => {
      this.fetchPendingIdeas();
      this.fetchApprovedIdeas();
    })

  }

  disapproveIdea(id: number) {
    this.ideaService.disapproveIdea(id).subscribe(() =>
      this.fetchPendingIdeas());
  }

  fetchPendingIdeas(): void {
    this.ideaService.fetchPendingIdeas().subscribe(
      (data) => {
        this.pendingIdeas = data.pendingIdeas;
      },
      (error) => {
        console.error("Error fetching pending ideas:", error);
      }
    );
  }

  fetchApprovedIdeas() {
    this.ideaService.fetchApprovedIdeas().subscribe((data) => {
        this.approvedIdeas = data.approvedIdeas;
      }, (error) => {
        console.error("Error fetching pending ideas:", error);
      }
    );
  }
}


