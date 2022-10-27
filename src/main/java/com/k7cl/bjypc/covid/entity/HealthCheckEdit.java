package com.k7cl.bjypc.covid.entity;

import java.sql.Timestamp;

public record HealthCheckEdit(String status, String result, Timestamp checkTime, String sid) {
}
