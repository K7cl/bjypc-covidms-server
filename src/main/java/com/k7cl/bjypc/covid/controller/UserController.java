package com.k7cl.bjypc.covid.controller;

import com.k7cl.bjypc.covid.entity.HealthCheck;
import com.k7cl.bjypc.covid.entity.User;
import com.k7cl.bjypc.covid.pojo.Response;
import com.k7cl.bjypc.covid.pojo.UserEdit;
import com.k7cl.bjypc.covid.pojo.UserHealth;
import com.k7cl.bjypc.covid.service.impl.ClassesService;
import com.k7cl.bjypc.covid.service.impl.HealthCheckService;
import com.k7cl.bjypc.covid.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    ClassesService classesService;
    @Autowired
    HealthCheckService healthCheckService;

    @GetMapping("/list")
    public Object get(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "limit", defaultValue = "10") int limit) {
        try {
            if (limit>0 && limit<50) {
                return new Response(true, null, userService.findAll(page, limit).get());
            } else {
                return new Response(false, "Limit value invalid!", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Response(false, e.getMessage(), null);
        }
    }

    @GetMapping("/search")
    public Object search(@RequestParam(value = "keyword") String keyword) {
        try {
            return new Response(true, null, userService.search(keyword));
        } catch (Exception e) {
            e.printStackTrace();
            return new Response(false, e.getMessage(), null);
        }
    }

    @GetMapping("/filter")
    public Object filter(@RequestParam(value = "name", defaultValue = "") String name, @RequestParam(value = "sid", defaultValue = "") String sid, @RequestParam(value = "cid", defaultValue = "0") int cid, @RequestParam(value = "identity", defaultValue = "") String identity, @RequestParam(value = "status", defaultValue = "true") boolean status, @RequestParam(value = "nat", defaultValue = "true") boolean nat) {
        try {
            List<User> users;
            if (cid != 0) {
                if (!classesService.isExists(cid))
                    return new Response(false, "Class not exist!", null);
                users = userService.filter(name, sid, classesService.findById(cid), identity);
            } else {
                users = userService.filterWOClass(name, sid, identity);
            }
            List<HealthCheck> hcs = healthCheckService.findAll();
            List<UserHealth> result = new ArrayList<>();
            for (User user : users) {
                HealthCheck healthCheck = getHcByUser(hcs, user);
                if (healthCheck == null) {
                    if (status && nat)
                        result.add(new UserHealth(user, null));
                    continue;
                }
                if (!status && !Objects.equals(healthCheck.getStatus(), "绿码")) {
                    result.add(new UserHealth(user, healthCheck));
                    continue;
                }
                if (!nat && daysBetweenNow(healthCheck.getCheckTime()) >= 3) {
                    result.add(new UserHealth(user, healthCheck));
                    continue;
                }
                if (status && nat)
                    result.add(new UserHealth(user, healthCheck));
            }
            return new Response(true, null, result);
        } catch (Exception e) {
            e.printStackTrace();
            return new Response(false, e.getMessage(), null);
        }
    }

    private HealthCheck getHcByUser(List<HealthCheck> hcs, User user) {
        for (HealthCheck hc : hcs) {
            if (Objects.equals(hc.getUser().getStudentId(), user.getStudentId())) {
                return hc;
            }
        }
        return null;
    }

    public static long daysBetweenNow(Timestamp one) {
        long difference = (one.getTime()-new Date(System.currentTimeMillis()).getTime())/86400000;
        return Math.abs(difference);
    }

    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.setDisallowedFields("id", "classes");
    }

    @PostMapping
    public Object add(@RequestBody UserEdit user) {
        try {
            if (userService.isExists(user.studentId())) {
                return new Response(false, "User exist!", null);
            }
            if (user.cId() != 0) {
                if (!classesService.isExists(user.cId()))
                    return new Response(false, "Class not exist!", null);
            }
            buildEntity(user, new User());
            return new Response(true, null, userService.findByStudentId(user.studentId()));
        } catch (Exception e) {
            e.printStackTrace();
            return new Response(false, e.getMessage(), null);
        }
    }

    @PutMapping("/{sid}")
    public Object edit(@PathVariable String sid, @RequestBody UserEdit reqUser) {
        try {
            if (!userService.isExists(sid)) {
                return new Response(false, "User not exist!", null);
            }
            if (!Objects.equals(reqUser.studentId(), sid) && userService.isExists(reqUser.studentId())) {
                return new Response(false, "User studentId conflict!", null);
            }
            if (reqUser.cId() != 0) {
                if (!classesService.isExists(reqUser.cId()))
                    return new Response(false, "Class not exist!", null);
                if (Objects.equals(classesService.findById(reqUser.cId()).getAdmin().getStudentId(), sid))
                    return new Response(false, "Class admin can not in same class!", null);
            }
            buildEntity(reqUser, userService.findByStudentId(sid));
            return new Response(true, null, userService.findByStudentId(reqUser.studentId()));
        } catch (Exception e) {
            e.printStackTrace();
            return new Response(false, e.getMessage(), null);
        }
    }

    @GetMapping("/{id}")
    public Object getOne(@PathVariable String id) {
        try {
            if (!userService.isExistsById(Integer.parseInt(id))) {
                return new Response(false, "User not exist!", null);
            }
            return new Response(true, null, userService.findById(Integer.parseInt(id)));
        } catch (Exception e) {
            e.printStackTrace();
            return new Response(false, e.getMessage(), null);
        }
    }

    private void buildEntity(@RequestBody UserEdit reqUser, User user) {
        user.setName(reqUser.name());
        user.setStudentId(reqUser.studentId());
        user.setFaculty(reqUser.faculty());
        if (reqUser.cId() != 0) {
            user.setClasses(classesService.findById(reqUser.cId()));
        } else {
            user.setClasses(null);
        }
        user.setIdentity(reqUser.identity());
        user.setPhone(reqUser.phone());
        userService.save(user);
    }

    @DeleteMapping("/{sid}")
    public Object my(@PathVariable String sid) {
        try {
            if (userService.delete(sid)) {
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
