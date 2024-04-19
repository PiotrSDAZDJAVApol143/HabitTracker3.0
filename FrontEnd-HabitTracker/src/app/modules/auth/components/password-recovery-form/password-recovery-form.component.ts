import { Component, OnInit } from '@angular/core';
import { FormService } from '../../../core/services/form.service';
import { PasswordsForm } from '../../../core/models/forms.model';
import { FormControl, FormGroup } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-password-recovery-form',
  templateUrl: './password-recovery-form.component.html',
  styleUrls: ['./password-recovery-form.component.scss'],
})
export class PasswordRecoveryFormComponent implements OnInit {
  passwordsForm: FormGroup<PasswordsForm> =
    this.formService.initPasswordsForm();

  get controls(): PasswordsForm {
    return this.passwordsForm.controls;
  }

  constructor(
    private formService: FormService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.route.paramMap.subscribe({
      next: (param) => {
        console.log(param.get('uid'));
      },
    });
  }

  getErrorMessage(control: FormControl<string>): string {
    return this.formService.getErrorMessage(control);
  }
}

