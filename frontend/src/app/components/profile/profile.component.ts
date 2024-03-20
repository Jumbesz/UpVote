import {Component, OnInit} from '@angular/core';
import {IdeaService} from "../../services/idea.service";
import {IdeaModel} from "../../models/idea.model";
import {AuthenticationService} from "../../services/authentication.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  approvedIdeas: IdeaModel[] = [];

  constructor(private ideaService: IdeaService,
              private authService: AuthenticationService,
              private router: Router) {
  }

  ngOnInit(): void {
    this.ideaService.fetchApprovedIdeas().subscribe(
      (data) => {
        this.approvedIdeas = data.approvedIdeas;
      },
      (error) => {
        console.error("Error fetching approved ideas:", error);
      }
    );
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['home'])
  }
}
