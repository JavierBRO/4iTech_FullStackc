import { Component, OnInit } from '@angular/core';
import { Room } from '../models/room.model';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { AuthenticationService } from '../authentication/authentication.service';
import { Keynote } from '../models/keynote.model';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-room-detail',
  standalone: true,
  imports: [RouterLink, DatePipe],
  templateUrl: './room-detail.component.html',
  styleUrl: './room-detail.component.css'
})
export class RoomDetailComponent implements OnInit {

  room: Room | undefined;
  keynotes: Keynote[] = [];
  isAdmin = false;

  constructor(
    private activatedRoute: ActivatedRoute,
    private httpClient: HttpClient,
    private authService: AuthenticationService){   
         this.authService.isAdmin.subscribe(isAdmin => this.isAdmin = isAdmin);
  }


  ngOnInit(): void {
    
    //Extraer el id de la url y traer la sala del backend con la petición HTTP GET:
    this.activatedRoute.params.subscribe(params => {
      const id = params['id'];
      if (!id) return;
    
      const backendUrl = 'http://localhost:8080/rooms/' + id;
      this.httpClient.get<Room>(backendUrl).subscribe(roomFromBackend => {
        this.room = roomFromBackend;
        //console.log(this.room);

          // TODO: Traer todas las charlas que se celebran en esta sala
      // /keynotes/filter-by-room/id
      
      this.httpClient.get<Keynote[]>('http://localhost:8080/keynotes/filter-by-room/' + id)
      .subscribe(keynotes => {
       this.keynotes = keynotes;
      });
    });
  });
  }
}

