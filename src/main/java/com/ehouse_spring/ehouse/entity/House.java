package com.ehouse_spring.ehouse.entity;

import jakarta.validation.constraints.NotBlank;

public class House {

    private Long id;

    @NotBlank
    private String name;

    public House() {
    }

    public House(@NotBlank String name) {
        this.name = name;
    }

    public House(Long id, @NotBlank String name) {
        this.id = id;
        this.name = name;
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
}
