
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { AuthenticationService } from '../authentication/authentication.service';
import { Router, RouterLink } from '@angular/router';
import { Login } from '../authentication/login.dto';
import { Token } from '../authentication/token.dto';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ReactiveFormsModule, HttpClientModule, RouterLink],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  loginForm = this.fb.group({
    email: [''],
    password: ['']
  });
  errorMessage = '';

  constructor(
    private fb: FormBuilder,
    private httpClient: HttpClient,
    private authService: AuthenticationService,
    private router: Router
     ) {}

  save() {
    const login: Login = {
      email: this.loginForm.get('email')?.value ?? '',
      password: this.loginForm.get('password')?.value ?? '',
    }
    // console.log(login);
    const url = 'http://localhost:8080/users/login';
    this.httpClient.post<Token>(url,login).subscribe({
      next: response => {
      console.log(response.token)
      this.authService.saveToken(response.token);
      this.router.navigate(['/keynotes']);
    },
    error: response => {
      console.log(response);
      this.errorMessage = response.error;
    }
  });
      
  }
}
