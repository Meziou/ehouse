package com.ehouse_spring.ehouse.entity;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class Room {

    private Long id;
    @NotBlank
    private String name;
    private LocalDate created_at;
    @NotNull
    private int id_house;
    
    public Room(@NotBlank String name, @NotBlank LocalDate created_at, @NotBlank int id_house) {
        this.name = name;
        this.created_at = created_at;
        this.id_house = id_house;
    }

    public Room(Long id, @NotBlank String name, @NotBlank LocalDate created_at, @NotBlank int id_house) {
        this.id = id;
        this.name = name;
        this.created_at = created_at;
        this.id_house = id_house;
    }

    public Room() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDate created_at) {
        this.created_at = created_at;
    }

    public int getId_house() {
        return id_house;
    }

    public void setId_house(int id_house) {
        this.id_house = id_house;
    }
    
   
    
}
