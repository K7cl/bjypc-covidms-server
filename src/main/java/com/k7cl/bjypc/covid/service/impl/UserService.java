package com.k7cl.bjypc.covid.service.impl;

import com.k7cl.bjypc.covid.entity.Classes;
import com.k7cl.bjypc.covid.entity.User;
import com.k7cl.bjypc.covid.service.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User findByStudentId(String sid) {
        return userRepository.findByStudentId(sid);
    }
    public List<User> findByPhone(String phone) {
        return userRepository.findByPhone(phone);
    }
    public List<User> findByClasses(Classes classes) {
        return userRepository.findByClasses(classes);
    }
    public List<User> findByName(String name) {
        return userRepository.findByNameContaining(name);
    }
    public User findById(int id) {
        return userRepository.findById(id);
    }
    public boolean isExistsById(int id) {
        return userRepository.existsById(id);
    }
    public boolean isExists(String sid) {
        return userRepository.existsByStudentId(sid);
    }


    public List<User> search(String keyword) {
        List<User> result = new ArrayList<>();
        result.add(userRepository.findByStudentId(keyword));
        result.addAll(userRepository.findByPhone(keyword));
        result.addAll(userRepository.findByNameContaining(keyword));
        result.remove(null);
        List<Integer> resultIds = new ArrayList<>();
        List<User> resultNew = new ArrayList<>();
        for (User user : result) {
            if (!resultIds.contains(user.getId())) {
                resultNew.add(user);
                resultIds.add(user.getId());
            }
        }
        return resultNew;
    }

    public List<User> filter(String name, String sid, Classes classes, String ident) {
        return userRepository.findByNameContainingAndStudentIdContainingAndClassesAndIdentityContaining(name, sid, classes, ident);
    }

    public List<User> filterWOClass(String name, String sid, String ident) {
        return userRepository.findByNameContainingAndStudentIdContainingAndIdentityContaining(name, sid, ident);
    }

    public boolean delete(String sid) {
        return userRepository.deleteByStudentId(sid) == 1;
    }

    public void save(User u) {
        userRepository.save(u);
    }

    public void delete(User u) {
        userRepository.delete(u);
    }

    public Page<User> findAll(int page, int limit) {
        Pageable pageable = PageRequest.of(page, limit);
        return userRepository.findAll(pageable);
    }

    public int getCont() {
        return userRepository.findAll().size();
    }
}
