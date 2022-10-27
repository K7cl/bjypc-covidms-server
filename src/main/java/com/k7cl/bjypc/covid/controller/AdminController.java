package com.k7cl.bjypc.covid.controller;

import com.k7cl.bjypc.covid.bean.Admin;
import com.k7cl.bjypc.covid.entity.Response;
import com.k7cl.bjypc.covid.service.impl.AdminService;
import com.k7cl.bjypc.covid.utils.Scrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    final Scrypt scrypt = new Scrypt();

    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.setDisallowedFields("id");
    }

    @PostMapping
    public Object add(@RequestBody Admin admin) {
        try {
            admin.setPassword(scrypt.encode(admin.getPassword()));
            adminService.save(admin);
            return new Response(true, null, null);
        } catch (Exception e) {
            e.printStackTrace();
            return new Response(false, e.getMessage(), null);
        }
    }

    @DeleteMapping("/{userName}")
    public Object my(@PathVariable String userName) {
        try {
            if (adminService.delete(userName)) {
                return new Response(true, null, null);
            } else {
                return new Response(false, null, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Response(false, e.getMessage(), null);
        }
    }
}
