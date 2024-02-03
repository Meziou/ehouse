package com.ehouse_spring.ehouse.entity;


import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class Device {
    
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String type;
    @NotBlank
    private String status;
    @NotNull
    private LocalDate created_at;
    @NotNull
    private int id_room;


    public @NotNull int getId_room() {
        return id_room;
    }



    public void setId_room(int id_room) {
        this.id_room = id_room;
    }



    public Device() {
    }



    public Device(Long id, @NotBlank String name, @NotBlank String type, @NotBlank String status,
            @NotNull LocalDate created_at, @NotNull int id_room) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.status = status;
        this.created_at = created_at;
        this.id_room = id_room;
    }



    public Device(@NotBlank String name, @NotBlank String type, @NotBlank String status,
            @NotBlank @NotNull LocalDate created_at, @NotNull int id_room) {
        this.name = name;
        this.type = type;
        this.status = status;
        this.created_at = created_at;
        this.id_room = id_room;
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



    public String getType() {
        return type;
    }



    public void setType(String type) {
        this.type = type;
    }



    public String getStatus() {
        return status;
    }



    public void setStatus(String status) {
        this.status = status;
    }



    public @NotNull LocalDate getCreated_at() {
        return created_at;
    }



    public void setCreated_at(@NotNull LocalDate created_at) {
        this.created_at = created_at;
    }

    

}