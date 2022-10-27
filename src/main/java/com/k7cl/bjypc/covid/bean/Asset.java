package com.k7cl.bjypc.covid.bean;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "asset")
public class Asset implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    private String name;
    private int count;
    private Timestamp importTime;
    @ManyToOne
    @JoinColumn(name = "admin_id")
    private User admin;
    @ManyToOne
    @JoinColumn(name = "classes_id")
    private Classes classes;

    public Classes getClasses() {
        return classes;
    }

    public void setClasses(Classes classes) {
        this.classes = classes;
    }

    public User getAdmin() {
        return admin;
    }

    public void setAdmin(User admin) {
        this.admin = admin;
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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Timestamp getImportTime() {
        return importTime;
    }

    public void setImportTime(Timestamp importTime) {
        this.importTime = importTime;
    }
}
