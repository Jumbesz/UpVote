import {Component, OnInit} from '@angular/core';
import {IdeaService} from "../../services/idea.service";
import {AuthenticationService} from "../../services/authentication.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {
  pendingIdeas: any;


  constructor(private ideaService: IdeaService,
              private authService: AuthenticationService,
              private router: Router) {
  }

  ngOnInit(): void {
    this.ideaService.fetchPendingIdeas().subscribe(
      (data) => {
        this.pendingIdeas = data.pendingIdeas;
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

  approveIdea(id: number) {

  }

  disapproveIdea(id: number) {

  }

}
