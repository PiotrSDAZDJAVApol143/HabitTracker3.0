import { Component } from '@angular/core';
import {FormService} from "../../../core/services/form.service";
import {FormControl} from "@angular/forms";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {
  loginForm = this.formService.initLoginForm();

  get controls() {
    return this.loginForm.controls;
  }


  constructor(private formService: FormService) {
  }

  getErrorMessage(control: FormControl) {
    return this.formService.getErrorMessage(control);
  }

  onSubmit() {
    if (this.loginForm.invalid) {
      this.loginForm.markAllAsTouched();
    } else {
      // logika logowania
    }

  }
}
