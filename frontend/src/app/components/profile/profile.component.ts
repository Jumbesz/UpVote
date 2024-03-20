import {Component, OnInit} from '@angular/core';
import {IdeaService} from '../../services/idea.service';
import {IdeaModel} from '../../models/idea.model';
import {AuthenticationService} from '../../services/authentication.service';
import {Router} from '@angular/router';
import {FormBuilder, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  approvedIdeas: IdeaModel[] = [];
  ideaForm!: FormGroup;

  constructor(private ideaService: IdeaService,
              private formBuilder: FormBuilder,
              private authService: AuthenticationService,
              private router: Router) {
  }

  ngOnInit(): void {
    this.ideaForm = this.formBuilder.group({
      name: [''],
      description: ['']
    });
    this.fetchApprovedIdeas();
  }

  fetchApprovedIdeas(): void {
    this.ideaService.fetchApprovedIdeas().subscribe(
      (data) => {
        this.approvedIdeas = data.approvedIdeas;
      },
      (error) => {
        console.error("Error fetching approved ideas:", error);
      }
    );
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['home'])
  }

  voteUp(id: number): void {
    this.ideaService.upVoteIdea(id).subscribe(
      () => {
        this.fetchApprovedIdeas();
      },
      (error) => {
        console.error("Error upvoting idea:", error);
      }
    );
  }

  voteDown(id: number): void {
    this.ideaService.downVoteIdea(id).subscribe(
      () => {
        this.fetchApprovedIdeas();
      },
      (error) => {
        console.error("Error downvoting idea:", error);
      }
    );
  }

  submitIdea(): void {
    this.ideaService.createNewIdea(this.ideaForm?.value).subscribe(() => {
      this.fetchApprovedIdeas()
      this.ideaForm?.reset();
    })
  }
}

