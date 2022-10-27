package com.k7cl.bjypc.covid.controller;

import com.k7cl.bjypc.covid.bean.HealthCheck;
import com.k7cl.bjypc.covid.entity.HealthCheckEdit;
import com.k7cl.bjypc.covid.entity.Response;
import com.k7cl.bjypc.covid.service.impl.HealthCheckService;
import com.k7cl.bjypc.covid.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/healthCheck")
public class HealthCheckController {
    @Autowired
    HealthCheckService healthCheckService;

    @Autowired
    UserService userService;


    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.setDisallowedFields("id", "user");
    }

    @PostMapping
    public Object add(@RequestBody HealthCheckEdit reqHealthCheck) {
        try {
            HealthCheck healthCheck = new HealthCheck();
            healthCheck.setCheckTime(reqHealthCheck.checkTime());
            healthCheck.setResult(reqHealthCheck.result());
            healthCheck.setStatus(reqHealthCheck.status());
            healthCheck.setUser(userService.findByStudentId(reqHealthCheck.sid()));
            healthCheckService.save(healthCheck);
            return new Response(true, null, null);
        } catch (Exception e) {
            e.printStackTrace();
            return new Response(false, e.getMessage(), null);
        }
    }

}
