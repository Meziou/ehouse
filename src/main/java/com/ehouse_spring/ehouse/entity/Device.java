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
    private LocalDate createDateTime;
    @NotBlank
    private String room;


    public Device() {
    }



    public Device(Integer id, @NotBlank String name, @NotBlank String type, @NotBlank String status,
            @NotBlank LocalDate createDateTime, @NotBlank String room) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.status = status;
        this.createDateTime = createDateTime;
        this.room = room;
    }



    public Device(@NotBlank String name, @NotBlank String type, @NotBlank String status,
            @NotBlank @NotBlank LocalDate createDateTime, @NotBlank String room) {
        this.name = name;
        this.type = type;
        this.status = status;
        this.createDateTime = createDateTime;
        this.room = room;
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



    public @NotBlank LocalDate getCreateDateTime() {
        return createDateTime;
    }



    public void setCreateDateTime(@NotBlank LocalDate createDateTime) {
        this.createDateTime = createDateTime;
    }



    public String getRoom() {
        return room;
    }



    public void setRoom(String room) {
        this.room = room;
    }
    

}