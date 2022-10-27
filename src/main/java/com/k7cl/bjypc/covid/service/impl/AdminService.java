package com.k7cl.bjypc.covid.service.impl;

import com.k7cl.bjypc.covid.bean.Admin;
import com.k7cl.bjypc.covid.service.AdminRepository;
import com.k7cl.bjypc.covid.utils.Scrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;

    final Scrypt scrypt = new Scrypt();

    public Admin findByUserName(String userName) {
        return adminRepository.findByUserName(userName);
    }

    public boolean delete(String userName) {
        return adminRepository.deleteByUserName(userName) == 1;
    }

    public void save(Admin u) {
        adminRepository.save(u);
    }

    public void delete(Admin u) {
        adminRepository.delete(u);
    }

    public boolean authenticate(String userName, String password) {
        Admin admin = adminRepository.findByUserName(userName);
        if (admin == null) {
            return false;
        }
        return scrypt.matches(password, admin.getPassword());
    }
}
