package com.remo.restapi.dtos;

public class CreateRoleDto {
    private String name;

    public CreateRoleDto() {
    }

    public CreateRoleDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
