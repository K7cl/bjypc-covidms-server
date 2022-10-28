package com.k7cl.bjypc.covid.service;

import com.k7cl.bjypc.covid.entity.HealthCheck;
import com.k7cl.bjypc.covid.entity.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HealthCheckRepository extends JpaRepository<HealthCheck, Long> {
    HealthCheck findFirstByUser(User user, Sort sort);
}
