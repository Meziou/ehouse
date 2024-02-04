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

    /**
     * @param device the device to be added
     * @return list of all the devices
     */
    @GetMapping
    public List<Device> getAll(){
        return repo.findAll();
    }

    /**
     * 
     * @param id the id of the device
     * @return the device with the given id
     */
    @GetMapping("/room/{id}")
    public List<Device> findByRoom(@PathVariable int id){
        return repo.findByRoom(id);
    }

    /**
     * 
     * @param id the id of the device
     * @return the device with the given id
     */
    @GetMapping("/{id}")
    public Device one(@PathVariable int id){
        Device device = repo.find(id);
        if (device == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return device;
    }

    /**
     * 
     * @param device the device to be added
     * @return the added device
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Device add(@Valid @RequestBody Device device) {
        repo.persist(device);
        return device;
    }
    
    /**
     * 
     * @param id the id of the device
     * @param device the device to be updated
     * @return the updated device
     */
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
    
    /**
     * @param id the id of the device
     * delete the device with the given id
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        if(!repo.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

}
