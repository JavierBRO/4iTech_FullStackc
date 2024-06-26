import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Component } from '@angular/core';
import { AbstractControl, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Register } from '../authentication/register.dto';
import { Router, RouterLink } from '@angular/router';
import { AuthenticationService } from '../authentication/authentication.service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [ReactiveFormsModule, HttpClientModule, RouterLink],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {
   // no necesita el formBuilder
   registerForm = new FormGroup({
    email: new FormControl ('', [Validators.required, Validators.email]), // hacerlo el campo obligatorio y que lleve @ y .com .es o similar
    // phone: new FormControl ('', [Validators.required, Validators.pattern('^[0-9]{9}$')]),  // valores de 0 a 9 y que sean 9 números
    password: new FormControl ('', [Validators.required, Validators.minLength(8), Validators.maxLength(30)]),
    passwordConfirm: new FormControl ('')
  },
   {validators: this.passwordConfirmValidator}  // validacion personalizada que comprueba que los dos campos sean iguales
  );

  errorMessage = '';
  successMessage = false;

  constructor(
    private httpClient: HttpClient,
    private authService: AuthenticationService,
    private router: Router
  ){}

  passwordConfirmValidator(control: AbstractControl) {

    if (control.get('password')?.value === control.get('passwordConfirm')?.value) {
      return null;  // coinciden valores en ambos campos... pues devolvemos null

    } else {
      //si no coinciden devolvemos un error:
      return {
        'confirmError': true
      }
    }
  }
  save() {
    const register: Register = {
      email: this.registerForm.get('email')?.value ??'',
      // phone: this.registerForm.get('phone')?.value ??'',
      password: this.registerForm.get('password')?.value ??''

    }
    console.log(register);
    this.httpClient.post('http://localhost:8080/users/register', register)
    .subscribe({
      next: response => {
      // navegar a login
      this.registerForm.reset(); 
      this.successMessage = true;
      // this.router.navigate(['/login']);
    },
    error: response => {
      console.log(response);
      this.errorMessage = response.error;
    }
  }); 


  }

}


