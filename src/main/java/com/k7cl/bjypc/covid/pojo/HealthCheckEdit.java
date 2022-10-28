package com.k7cl.bjypc.covid.pojo;

import java.sql.Timestamp;

public record HealthCheckEdit(String status, String result, Timestamp checkTime, String sid) {
}
