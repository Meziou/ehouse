package com.ehouse_spring.ehouse.entity;

import jakarta.validation.constraints.NotBlank;

public class House {

    private Integer id;

    @NotBlank
    private String name;

    public House() {
    }

    public House(@NotBlank String name) {
        this.name = name;
    }

    public House(Integer id, @NotBlank String name) {
        this.id = id;
        this.name = name;
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
}
