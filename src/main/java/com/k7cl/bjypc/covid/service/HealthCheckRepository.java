package com.k7cl.bjypc.covid.service;

import com.k7cl.bjypc.covid.bean.HealthCheck;
import com.k7cl.bjypc.covid.bean.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HealthCheckRepository extends JpaRepository<HealthCheck, Long> {
    HealthCheck findFirstByUser(User user, Sort sort);
}
