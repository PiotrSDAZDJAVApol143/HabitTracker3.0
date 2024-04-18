import { NgModule } from '@angular/core';

import { AuthRoutingModule } from './auth-routing.module';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import {ReactiveFormsModule} from "@angular/forms";
import {NgIf} from "@angular/common";

@NgModule({
  declarations: [LoginComponent, RegisterComponent],
    imports: [AuthRoutingModule, MatFormFieldModule, MatButtonModule, MatInputModule, ReactiveFormsModule, NgIf],
})
export class AuthModule {}

