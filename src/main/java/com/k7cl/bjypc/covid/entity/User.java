package com.k7cl.bjypc.covid.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "user")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    private String name;
    private String studentId;
    private String faculty;
    @ManyToOne
    @JoinColumn(name = "classes_id")
    private Classes classes;
    private String identity;
    private String phone;
    @OneToMany(mappedBy = "admin")
    private List<Asset> assetList;
    @OneToMany(mappedBy = "admin")
    private List<Classes> classesList;

    @PreRemove
    private void preRemove() {
        for (Asset s : assetList) {
            s.setAdmin(null);
        }
        for (Classes s : classesList) {
            s.setAdmin(null);
        }
    }

    public Classes getClasses() {
        return classes;
    }

    public void setClasses(Classes classes) {
        this.classes = classes;
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

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
