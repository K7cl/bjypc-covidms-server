package com.k7cl.bjypc.covid.controller;

import com.k7cl.bjypc.covid.bean.HealthCheck;
import com.k7cl.bjypc.covid.bean.User;
import com.k7cl.bjypc.covid.entity.Response;
import com.k7cl.bjypc.covid.entity.StatusRes;
import com.k7cl.bjypc.covid.service.impl.HealthCheckService;
import com.k7cl.bjypc.covid.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
public class CovidController {
    @Autowired
    UserService userService;
    @Autowired
    HealthCheckService healthCheckService;

    @GetMapping("/status")
    public Object get() {
        try {
            int count = userService.getCont();
            int status = 0;
            int nat = 0;
            List<User> users = new ArrayList<>();
            List<HealthCheck> hcs = healthCheckService.findAll();
            for (HealthCheck hc : hcs) {
                if (users.contains(hc.getUser())) {
                    continue;
                }
                if (!Objects.equals(hc.getStatus(), "绿码")) {
                    status++;
                }
                if (UserController.daysBetweenNow(hc.getCheckTime()) >= 3) {
                    nat++;
                }
                users.add(hc.getUser());
            }
            return new Response(true, null, new StatusRes(count, status, nat));
        } catch (Exception e) {
            e.printStackTrace();
            return new Response(false, e.getMessage(), null);
        }
    }


}
