import { FormControl } from '@angular/forms';
export interface PasswdRecoveryForm {
  email: FormControl<string>;
}
export interface PasswordsForm {
  password: FormControl<string>;
  repeatedPassword: FormControl<string>;
}
export interface LoginForm {
  login: FormControl<string>;
  password: FormControl<string>;
}
// zrobiliśmy extends LoginForm aby nie wypisywać wszystkich informacji które były już podane wyżej
export interface RegisterForm extends LoginForm {
  email: FormControl<string>;
  repeatedPassword: FormControl<string>;
}

