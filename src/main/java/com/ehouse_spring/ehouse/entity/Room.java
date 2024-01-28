package com.ehouse_spring.ehouse.entity;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;

public class Room {

    private Integer id;

    @NotBlank
    private String name;

    @NotBlank
    private LocalDateTime createdDateTime;

    public Room() {
    }

    public Room(@NotBlank String name, @NotBlank LocalDateTime createdDateTime) {
        this.name = name;
        this.createdDateTime = createdDateTime;
    }

    public Room(Integer id, @NotBlank String name, @NotBlank LocalDateTime createdDateTime) {
        this.id = id;
        this.name = name;
        this.createdDateTime = createdDateTime;
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

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(LocalDateTime createdDateTime) {
        this.createdDateTime = createdDateTime;
    }
    
}
