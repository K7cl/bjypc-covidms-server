package com.k7cl.bjypc.covid.entity;

import java.sql.Timestamp;

public record AssetEdit(int aid, String name, int count, Timestamp importTime, String sid, int cid) {
}
