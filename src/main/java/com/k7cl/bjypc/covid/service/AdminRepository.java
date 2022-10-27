package com.k7cl.bjypc.covid.service;

import com.k7cl.bjypc.covid.bean.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    Admin findByUserName(String userName);
    boolean deleteByUserName(String userName);
}
