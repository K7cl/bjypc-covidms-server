package com.k7cl.bjypc.covid.service.impl;

import com.k7cl.bjypc.covid.bean.Classes;
import com.k7cl.bjypc.covid.service.ClassesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ClassesService {
    @Autowired
    private ClassesRepository classesRepository;

    public List<Classes> findByName(String name) {
        return classesRepository.findByName(name);
    }
    public Classes findById(int id) {
        return classesRepository.findById(id);
    }

    public boolean isExists(int cid) {
        return classesRepository.existsById(cid);
    }

    public void save(Classes u) {
        classesRepository.save(u);
    }

    public void delete(Classes u) {
        classesRepository.delete(u);
    }

    public boolean delete(int id) {
        return classesRepository.deleteById(id);
    }

    public Page<Classes> findAll(int page, int limit) {
        Pageable pageable = PageRequest.of(page, limit);
        return classesRepository.findAll(pageable);
    }
}
