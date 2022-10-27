package com.k7cl.bjypc.covid.bean;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

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
    @OneToMany(mappedBy = "classes")
    private List<Asset> assetList;

    @PreRemove
    private void preRemove() {
        for (Asset s : assetList) {
            s.setClasses(null);
        }
    }

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
