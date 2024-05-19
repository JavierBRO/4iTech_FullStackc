package com.iTech.controller;

import com.iTech.models.Room;
import com.iTech.repository.RoomRepository;
import com.iTech.services.FileService;
import com.iTech.services.RoomService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
@CrossOrigin("*")
@RestController
public class RoomController {
    List<Room> rooms;
    private final RoomRepository roomRepository;
    private FileService fileService;

    public RoomController(RoomService service, RoomRepository roomRepository){
        this.roomRepository = roomRepository;
    }

    @GetMapping("rooms")
    public ResponseEntity<List<Room>> findAll(){
        return ResponseEntity.ok(roomRepository.findAll());
    }
    @GetMapping("rooms/{id}")
    public ResponseEntity<Room> findById(@PathVariable Long id) {
        Optional<Room> roomOptional = roomRepository.findById(id);
        if (roomOptional.isPresent())
            return ResponseEntity.ok(roomOptional.get());
        else
            return ResponseEntity.notFound().build();
    }
    /*
    // OPCION 1
    @PostMapping("rooms")
    public ResponseEntity<Room> create(@RequestBody Room room){
        return ResponseEntity.ok(roomRepository.save(room));
    }
    @PutMapping("rooms/{id}")
    public ResponseEntity<Room> update(@PathVariable Long id, @RequestBody Room room){
        if(!roomRepository.existsById(id)) return ResponseEntity.notFound().build();
        Optional<Room> roomOptional = roomRepository.findById(id);
        if(roomOptional.isPresent()){
            Room roomFromDB = roomOptional.get();
            roomFromDB.setCapacity(room.getCapacity()); // este método solo permite modificar el aforo de la sala.
            roomRepository.save(roomFromDB);
            return ResponseEntity.ok(roomFromDB);
        } else
            return ResponseEntity.notFound().build();
    }
    */


     // OPCION 2, TENIENDO EN CUENTA MULTIPARTFILE DE ROOM

    @PostMapping("rooms")
    public Room create(
            @RequestParam(value = "photo", required = false) MultipartFile file, Room room) {

        if (file != null && !file.isEmpty()) {
            String fileName = fileService.store(file);
            room.setPhotoUrl(fileName);
        } else {
            room.setPhotoUrl("room.01.jpg");
        }

        return this.roomRepository.save(room);
    }


    @PutMapping("rooms/{id}")
    public ResponseEntity<Room> update(@RequestParam(value = "photo", required = false) MultipartFile file,
                                          Room room,
                                          @PathVariable Long id) {

        if (!this.roomRepository.existsById(id))
            return ResponseEntity.notFound().build();

        if (file != null && !file.isEmpty()) {
            String fileName = fileService.store(file);
            room.setPhotoUrl(fileName);
        }

        return ResponseEntity.ok(this.roomRepository.save(room));
    }


    @DeleteMapping("rooms/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        roomRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
