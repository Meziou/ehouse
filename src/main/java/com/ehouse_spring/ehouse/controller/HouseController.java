package com.ehouse_spring.ehouse.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.ehouse_spring.ehouse.entity.House;
import com.ehouse_spring.ehouse.entity.repository.interfaces.HouseRepository;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/house")
public class HouseController {
    @Autowired
    private HouseRepository repo;

    /**
     * @param house the house to be added
     * @return list of all the houses
     */
    @GetMapping
    public List<House> getAll(){
        return repo.findAll();
    }

    /**
     * 
     * @param id the id of the house
     * @return the house with the given id
     */
    @GetMapping("/{id}")
    public House one(@PathVariable int id){
        House house = repo.find(id);
        if (house == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return house;
    }

    /**
     * 
     * @param house the house to be added
     * @return the added house
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public House add(@Valid @RequestBody House house) {
        repo.persist(house);
        return house;
    }
    
    /**
     * 
     * @param id the id of the house
     * @param house the house to be updated
     * @return the updated house
     */
    @PatchMapping("/{id}")
    public House update(@PathVariable int id, @RequestBody House house){
        House toUpdate = one(id);
        if (house.getName() != null) {
            toUpdate.setName(house.getName());
        }

        repo.update(toUpdate);
        return toUpdate;
    }

    /**
     * @param id the id of the house
     * delete the house with the given id
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id){
        if (!repo.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
