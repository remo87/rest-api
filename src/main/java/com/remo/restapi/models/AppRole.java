package com.remo.restapi.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "app_roles")
public class AppRole {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @ManyToMany
    private List<AppUser> users;
}
