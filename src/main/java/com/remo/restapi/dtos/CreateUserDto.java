package com.remo.restapi.dtos;

import java.util.Date;
import java.util.List;

public class CreateUserDto {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String photo;
    private String phone;
    private Date dateOfBirth;
    private List<RoleDto> roles;

    public CreateUserDto() {
    }

    public CreateUserDto(String firstName, String lastName, String username, String password, String photo, String phone, Date dateOfBirth, List<RoleDto> roles) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.photo = photo;
        this.phone = phone;
        this.dateOfBirth = dateOfBirth;
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public List<RoleDto> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleDto> roles) {
        this.roles = roles;
    }
}
