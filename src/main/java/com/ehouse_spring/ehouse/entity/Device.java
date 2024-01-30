package com.ehouse_spring.ehouse.entity;


import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;

public class Device {
    
    private Integer id;
    @NotBlank
    private String name;
    @NotBlank
    private String type;
    @NotBlank
    private String status;
    @NotBlank
    private LocalDate created_at;
    @NotBlank
    private String id_room;


    public String getId_room() {
        return id_room;
    }



    public void setId_room(String id_room) {
        this.id_room = id_room;
    }



    public Device() {
    }



    public Device(Integer id, @NotBlank String name, @NotBlank String type, @NotBlank String status,
            @NotBlank LocalDate created_at, @NotBlank String id_room) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.status = status;
        this.created_at = created_at;
        this.id_room = id_room;
    }



    public Device(@NotBlank String name, @NotBlank String type, @NotBlank String status,
            @NotBlank @NotBlank LocalDate created_at, @NotBlank String id_room) {
        this.name = name;
        this.type = type;
        this.status = status;
        this.created_at = created_at;
        this.id_room = id_room;
    }



    public Integer getId() {
        return id;
    }



    public void setId(Integer id) {
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



    public @NotBlank LocalDate getcreated_at() {
        return created_at;
    }



    public void setcreated_at(@NotBlank LocalDate created_at) {
        this.created_at = created_at;
    }

    

}