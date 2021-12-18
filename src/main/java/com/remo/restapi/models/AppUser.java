package com.remo.restapi.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "app_users")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@Data @AllArgsConstructor @NoArgsConstructor
public class AppUser implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String lastname;
    private String username;
    private String password;
    private String photo;
    private String phone;
    private Date dOfBirth;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<AppRole> roles;
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateAt;

    public AppUser(Long id, String name, String lastname, String username, String password, String photo, String phone, Date dOfBirth, List<AppRole> roles) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        this.photo = photo;
        this.phone = phone;
        this.dOfBirth = dOfBirth;
        this.roles = roles;
    }

    @PrePersist
    public void prePersist() {
        createdAt = new Date();
    }

    @PreUpdate
    public void preUpdate() {
        updateAt = new Date();
    }
}
