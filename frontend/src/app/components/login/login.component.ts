import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AuthenticationService} from "../../services/authentication.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loginForm!: FormGroup;

  constructor(private formBuilder: FormBuilder,
              private authenticationService: AuthenticationService,
              private router: Router) {
  }

  ngOnInit(): void {
    this.loginForm = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  login() {
    if (this.loginForm.valid) {
      const credentials = this.loginForm.value;
      this.authenticationService.login(credentials.username, credentials.password).subscribe((result) => {
        this.authenticationService.setCurrentUser(result);
        this.authenticationService.storeCurrentUser(result);
        if (this.authenticationService.isAdmin) {
          this.router.navigate(['/admin']);
        } else {
          this.router.navigate(['/profile']);
        }
      })
    }
  }
}
