import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { Ticket } from '../models/ticket.model';
import { NgbAlertModule } from '@ng-bootstrap/ng-bootstrap';
import { AuthenticationService } from '../authentication/authentication.service';

@Component({
  selector: 'app-ticket-list',
  standalone: true,
  imports: [RouterLink,NgbAlertModule],
  templateUrl: './ticket-list.component.html',
  styleUrl: './ticket-list.component.css'
})
export class TicketListComponent implements OnInit {
[x: string]: any;
handleTableClick() {
throw new Error('Method not implemented.');
}
  tickets: Ticket[] = [];
  showDeletedTicketMessage: boolean = false;



  isAdmin = false;
  isLoggedIn = false;

  constructor(private http: HttpClient,
              private authService: AuthenticationService
  ) {
    this.authService.isAdmin.subscribe(isAdmin => this.isAdmin = isAdmin);
    this.authService.isLoggedIn.subscribe(isLoggedIn => this.isLoggedIn = isLoggedIn);

  };

  


  ngOnInit(): void {
    this.loadTickets();
  }

  delete(ticket: Ticket) {
    const backendUrl = 'http://localhost:8080/tickets/' + ticket.id;
    this.http.delete(backendUrl).subscribe(response => {this.loadTickets()
    this.showDeletedTicketMessage = true;
    
  });
  }

hideDeletedTicketMessage() {
      this.showDeletedTicketMessage = false;
    }

    private loadTickets() {
    const url = 'http://localhost:8080/tickets';
    this.http.get<Ticket[]>(url).subscribe(tickets => this.tickets = tickets);
  }
}

