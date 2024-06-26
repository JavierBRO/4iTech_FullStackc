package com.iTech.controller;

import com.iTech.exception.ConflictDeleteException;
import com.iTech.models.Keynote;
import com.iTech.models.Track;
import com.iTech.models.User;
import com.iTech.models.UserRole;
import com.iTech.repository.CommentRepository;
import com.iTech.repository.KeynoteRepository;
import com.iTech.repository.TrackRepository;
import com.iTech.security.SecurityUtils;
import com.iTech.services.TrackService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@AllArgsConstructor
@Slf4j
public class TrackController {
    

    private final TrackService trackService;
    private TrackRepository trackRepository;
    private  CommentRepository commentRepository;
    private  KeynoteRepository keynoteRepository;
    


    @GetMapping("tracks")
   public ResponseEntity<List<Track>> findAll() {
        //System.out.println("invocando findAll de tracks");
        List<Track> track = trackService.findTrackVisibleTrue();
        return ResponseEntity.ok(track);
    }
    @GetMapping("tracks/{id}")
    public ResponseEntity<Track> findById(@PathVariable Long id){
       Track track = trackService.findById(id);
       if (track!=null) {
           return ResponseEntity.ok(track);
       } else {
           return ResponseEntity.notFound().build();
       }
    }
   @PostMapping("tracks")
    public ResponseEntity<Track> createTrack(@RequestBody Track track) {
        Track createdTrack = trackService.createTrack(track);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTrack);
        //return ResponseEntity.ok(trackRepository.save(track)); es lo mismo

   }
    @PutMapping("tracks/{id}")
    public ResponseEntity<Track> updateTrack(@PathVariable Long id, @RequestBody Track track) {
        Track updatedTrack = trackService.updateTrack(id,track);
        if (updatedTrack != null) {
            return ResponseEntity.ok(updatedTrack);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    
      @DeleteMapping("tracks/{id}")
        public void deleteById(@PathVariable Long id) {


           // OPCION 1 : borrar el track, pero antes desasociar o borrar aquellos objetos que apunten track
    //     Track track = this.trackRepository.findById(id).orElseThrow();
    //     User user = SecurityUtils.getCurrentUser().orElseThrow();

    // if (user.getUserRole().equals(UserRole.ADMIN)
    // )
    //     try {
    //         this.commentRepository.deleteByKeynoteId(id);
    //         this.keynoteRepository.deleteByTrackId(id);
    //         this.trackRepository.deleteById(id);
    //     } catch (Exception e) {
    //         log.error("Error borrando track", e);
    //         throw new ConflictDeleteException("No es posible borrar el track.");
    //     }
    // }
               // OPCION 2 mejor TODO: Archivar tracks y rooms da menos problemas,
               //.. (al no tener que borrar asociaciones), con las Foreing keys
        Keynote keynote = this.keynoteRepository.findById(id).orElseThrow();
        Track track = this.trackRepository.findById(id).orElseThrow();
        User user = SecurityUtils.getCurrentUser().orElseThrow();
        if (user.getUserRole().equals(UserRole.ADMIN)) {
            keynote.setVisible(false);
            keynoteRepository.save(keynote);
            track.setVisible(false);
            trackRepository.save(track);
        }
        
    }
        

}     

    // @DeleteMapping("tracks/{id}")
    // public ResponseEntity<Void> deleteTrack(@PathVariable Long id) {
    //        boolean deleted = trackService.deleteTrack(id);
    //        if (deleted) {
    //            return ResponseEntity.noContent().build();
    //        } else {
    //            return ResponseEntity.notFound().build();
    //        }
    // }

// Otra forma llamando el controlador directamente al repositorio sería:
// @AllArgsConstructor
// @RestController
// public class TrackController {
//     private final TrackRepository trackRepository;

//     @GetMapping("tracks")
//     public List<Track> findAll() {
//         return trackRepository.findAll();
//     }

//     @GetMapping("tracks/{id}")
//     public ResponseEntity<Track> findById(@PathVariable Long id){
//         Optional<Track> trackOptional = trackRepository.findById(id);

//         if(trackOptional.isPresent()){
//             return ResponseEntity.ok(trackOptional.get());
//         } else {
//             return ResponseEntity.notFound().build();
//         }
//         // Metodo Funcional equivalente al if - else:
//         //return trackOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
//     }
//     @PostMapping("tracks")
//     public ResponseEntity<Track> create(@RequestBody Track track) {
//         return ResponseEntity.ok(trackRepository.save(track));
//     }
//     @PutMapping("tracks/{id}")
//     public ResponseEntity<Track> update(@PathVariable Long id, @RequestBody Track track) {
//         Optional<Track> trackOpt = trackRepository.findById(id);

//         if(trackOpt.isEmpty()){
//             return ResponseEntity.notFound().build();
//         }  // permitir solo actualizar estos atributos y no todos

//         trackOpt.get().setName(track.getName());
//         trackOpt.get().setStartDate(track.getStartDate());
//         trackOpt.get().setEndDate(track.getEndDate());
//         return ResponseEntity.ok(trackRepository.save(trackOpt.get()));
//     }
//     @DeleteMapping("tracks")
//     public ResponseEntity<Void> deleteAll() {
//         // No será habitual Borrar completo Ya que si hay asociaciones puede dar problemas claves foraneas o hay que desasociar
//         trackRepository.deleteAll();
//         return ResponseEntity.noContent().build(); // 204 ?
//     }
//         @DeleteMapping("tracks/{id}")
//     public ResponseEntity<Void> deleteById(@PathVariable Long id) {
//             // SE puede agregar un repo.existsById(id)
//             trackRepository.deleteById(id);
//             return ResponseEntity.noContent().build(); // 204


//     }
// }

