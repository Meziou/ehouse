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

import com.ehouse_spring.ehouse.entity.Device;
import com.ehouse_spring.ehouse.entity.repository.interfaces.DeviceRepository;

import jakarta.validation.Valid;


@RestController //annotation obligatoire  pour faire un contrôleur de type rest
@RequestMapping("/api/device") //permet de mettre un prefix à toutes les routes définies dans ce contrôleur
public class DeviceController {

    @Autowired // Permet d'injecter un autre composant connu de spring dans cette classe (spring se charge de l'instanciation)
    private DeviceRepository repo;

    @GetMapping
    public List<Device> getAll(){
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public Device one(@PathVariable int id){
        Device device = repo.find(id);
        if (device == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return device;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Device add(@Valid @RequestBody Device device) {
        repo.persist(device);
        return device;
    }
    
    @PatchMapping("/{id}")
    public Device update(@PathVariable int id, @RequestBody Device device) {
        Device toUpdate = one(id);
        if(device.getName() != null) {
            toUpdate.setName(device.getName());
        }
        if(device.getType() != null) {
            toUpdate.setType(device.getType());
        }
        
        if(device.getStatus() != null) {
            toUpdate.setStatus(device.getStatus());
        }
        
        if(device.getCreated_at() != null) {
            toUpdate.setCreated_at(device.getCreated_at());
        }
        
        repo.update(toUpdate);
        return toUpdate;
    }
    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        if(!repo.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

}
