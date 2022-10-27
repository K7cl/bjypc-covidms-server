package com.k7cl.bjypc.covid.service;

import com.k7cl.bjypc.covid.bean.Classes;
import com.k7cl.bjypc.covid.bean.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassesRepository extends JpaRepository<Classes, Long> {
    List<Classes> findByName(String name);
    boolean existsById(int id);
    Classes findById(int id);
    boolean deleteById(int id);
}
