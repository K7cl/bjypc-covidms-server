package com.k7cl.bjypc.covid.entity;

import com.k7cl.bjypc.covid.bean.HealthCheck;
import com.k7cl.bjypc.covid.bean.User;

public record UserHealth(User user, HealthCheck healthCheck) {
}
