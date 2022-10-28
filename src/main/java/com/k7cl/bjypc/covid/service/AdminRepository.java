package com.k7cl.bjypc.covid.service;

import com.k7cl.bjypc.covid.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    Admin findByUserName(String userName);
    @Transactional
    int deleteByUserName(String userName);
}
