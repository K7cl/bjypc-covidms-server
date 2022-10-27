package com.k7cl.bjypc.covid.bean;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "classes")
public class Classes implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User admin;

    public User getAdmin() {
        return admin;
    }

    public void setAdmin(User user) {
        this.admin = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
