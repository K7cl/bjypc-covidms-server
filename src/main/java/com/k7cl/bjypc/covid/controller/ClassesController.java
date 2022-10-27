package com.k7cl.bjypc.covid.controller;

import com.k7cl.bjypc.covid.bean.Classes;
import com.k7cl.bjypc.covid.entity.ClassEdit;
import com.k7cl.bjypc.covid.entity.Response;
import com.k7cl.bjypc.covid.service.impl.ClassesService;
import com.k7cl.bjypc.covid.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import java.util.Objects;

@RestController
@RequestMapping("/class")
public class ClassesController {
    @Autowired
    UserService userService;
    @Autowired
    ClassesService classesService;

    @GetMapping("/list")
    public Object get(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "limit", defaultValue = "10") int limit) {
        if (limit>0 && limit<50) {
            return new Response(true, null, classesService.findAll(page, limit).get());
        } else {
            return new Response(false, "Limit value invalid!", null);
        }
    }

    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.setDisallowedFields("id", "admin");
    }

    @PostMapping
    public Object add(@RequestBody ClassEdit reqClass) {
        try {
            if (!userService.isExists(reqClass.sid())) {
                return new Response(false, "Admin user not exist!", null);
            }
            buildEntity(reqClass, new Classes());
            return new Response(true, null, null);
        } catch (Exception e) {
            e.printStackTrace();
            return new Response(false, e.getMessage(), null);
        }
    }

    @PutMapping
    public Object edit(@RequestBody ClassEdit reqClass) {
        try {
            if (!classesService.isExists(reqClass.cid())) {
                return new Response(false, "Class not exist!", null);
            }
            if (!userService.isExists(reqClass.sid()))
                return new Response(false, "Admin user not exist!", null);
            Classes adminClass = userService.findByStudentId(reqClass.sid()).getClasses();
            if (adminClass != null)
                if (Objects.equals(adminClass.getId(), reqClass.cid()))
                    return new Response(false, "Class admin can not in same class!", null);
            buildEntity(reqClass, classesService.findById(reqClass.cid()));
            return new Response(true, null, classesService.findById(reqClass.cid()));
        } catch (Exception e) {
            e.printStackTrace();
            return new Response(false, e.getMessage(), null);
        }
    }

    @GetMapping("/{id}")
    public Object getOne(@PathVariable String id) {
        try {
            if (!classesService.isExists(Integer.parseInt(id))) {
                return new Response(false, "User not exist!", null);
            }
            return new Response(true, null, classesService.findById(Integer.parseInt(id)));
        } catch (Exception e) {
            e.printStackTrace();
            return new Response(false, e.getMessage(), null);
        }
    }

    private void buildEntity(@RequestBody ClassEdit reqClass, Classes classes) {
        classes.setName(reqClass.name());
        classes.setAdmin(userService.findByStudentId(reqClass.sid()));
        classesService.save(classes);
    }

    @DeleteMapping("/{cid}")
    public Object my(@PathVariable int cid) {
        try {
            if (classesService.delete(cid)) {
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
