package com.ehouse_spring.ehouse.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.ehouse_spring.ehouse.entity.Room;
import com.ehouse_spring.ehouse.entity.repository.interfaces.RoomRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/room")
public class RoomController {

    @Autowired
    private RoomRepository repo;

    @GetMapping
    public List<Room> getAll(){
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public Room one(@PathVariable int id){
        Room room = repo.find(id);
        if (room == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return room;

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Room add(@Valid @RequestBody Room room) {
        repo.persist(room);
        return room;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        if(!repo.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{id}")
    public Room update(@PathVariable int id, @RequestBody Room room) {
        Room toUpdate = one(id);
        if(room.getName() != null) {
            toUpdate.setName(room.getName());
        }
        if(room.getCreated_at() != null) {
            toUpdate.setCreated_at(room.getCreated_at());
        }

        repo.update(toUpdate);
        return toUpdate;
    }
}
