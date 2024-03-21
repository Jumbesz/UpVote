import {Component, OnInit} from '@angular/core';
import {AbstractControl, FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AuthenticationService} from "../../services/authentication.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  registrationForm!: FormGroup;

  constructor(private formBuilder: FormBuilder,
              private authenticationService: AuthenticationService,
              private router: Router,
  ) {
  }

  ngOnInit() {
    this.registrationForm = this.formBuilder.group({
        username: ['', {
          validators: [Validators.required],
          updateOn: 'change'
        }],
        password: ['', {
          validators: [Validators.required, this.passwordValidator],
          updateOn: 'change'
        }],
        confirmPassword: ['', {
          validators: [Validators.required],
          updateOn: 'change'
        }]
      },
      {validators: this.confirmPasswordValidator}
    );

    this.registrationForm.get('password')?.valueChanges.subscribe(() => {
      this.registrationForm.get('confirmPassword')?.updateValueAndValidity();
    });
  }

  register() {

    if (this.registrationForm.valid) {
      this.authenticationService.register(this.registrationForm.value).subscribe(
        (response) => {
          console.log(response);
          this.router.navigate(['/home'])
        }

        , (error) => {
          if (error.status === 406) {
            console.log("Username is already taken!")
            this.registrationForm.get('username')?.setErrors({usernameTaken: true})
          }
        }
      );
    } else {
      console.error('Form is not valid!');
    }

  }

  confirmPasswordValidator(formGroup: FormGroup) {
    const passwordControl = formGroup.get('password');
    const confirmPasswordControl = formGroup.get('confirmPassword');

    if (passwordControl && confirmPasswordControl && passwordControl.value !== confirmPasswordControl.value) {
      confirmPasswordControl.setErrors({passwordMismatch: true});
    }
  }

  passwordValidator(control: AbstractControl) {
    const passwordValue = control.value;
    if (passwordValue && passwordValue.length < 3) {
      return {passwordTooShort: true};
    }
    return null;
  }


}

