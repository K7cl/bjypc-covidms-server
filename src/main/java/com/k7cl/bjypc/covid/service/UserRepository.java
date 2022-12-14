package com.k7cl.bjypc.covid.service;

import com.k7cl.bjypc.covid.entity.Classes;
import com.k7cl.bjypc.covid.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByClasses(Classes classes);
    List<User> findByPhone(String phone);
    User findByStudentId(String sid);
    List<User> findByNameContaining(String name);
    User findById(int id);

    List<User> findByNameContainingAndStudentIdContainingAndClassesAndIdentityContaining(String name, String sid, Classes classes, String ident);
    List<User> findByNameContainingAndStudentIdContainingAndIdentityContaining(String name, String sid, String ident);
    boolean existsByStudentId(String sid);
    boolean existsById(int id);
    @Transactional
    int deleteByStudentId(String sid);
}
