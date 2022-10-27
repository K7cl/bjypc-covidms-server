package com.k7cl.bjypc.covid.service.impl;

import com.k7cl.bjypc.covid.bean.HealthCheck;
import com.k7cl.bjypc.covid.bean.User;
import com.k7cl.bjypc.covid.service.HealthCheckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HealthCheckService {
    @Autowired
    private HealthCheckRepository healthCheckRepository;

    public void save(HealthCheck u) {
        healthCheckRepository.save(u);
    }

    public void delete(HealthCheck u) {
        healthCheckRepository.delete(u);
    }

    public HealthCheck findLatestByUser(User user) {
        Sort sort = Sort.by(Sort.Direction.DESC, "checkTime");
        return healthCheckRepository.findFirstByUser(user, sort);
    }

    public List<HealthCheck> findAll() {
        Sort sort = Sort.by(Sort.Direction.DESC, "checkTime");
        return healthCheckRepository.findAll(sort);
    }

}
