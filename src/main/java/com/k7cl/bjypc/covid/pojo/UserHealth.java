package com.k7cl.bjypc.covid.pojo;

import com.k7cl.bjypc.covid.entity.HealthCheck;
import com.k7cl.bjypc.covid.entity.User;

public record UserHealth(User user, HealthCheck healthCheck) {
}
